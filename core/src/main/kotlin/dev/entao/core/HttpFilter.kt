@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.entao.core

import dev.entao.base.ownerClass
import dev.entao.log.*
import java.io.File
import java.util.*
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

/**
 * Created by entaoyang@163.com on 2016/12/21.
 */


typealias HttpAction = KFunction<*>

//    @WebFilter中的urlPatterns =  "/*"
abstract class HttpFilter : Filter {

	open var sessionTimeoutSeconds: Int = 3600
	open var appName: String = "主页"

	lateinit var filterConfig: FilterConfig
		private set

	var contextPath: String = ""
		private set

	// /* => "" , /person/*  => person     @WebFilter中的urlPatterns
	var patternPath: String = ""
		private set

	val webDir: WebDir by lazy { WebDir(this) }

	val routeManager: HttpActionManager by lazy {
		HttpActionManager(this)
	}
	val sliceList: ArrayList<HttpSlice> = ArrayList()

	val infoMap = HashMap<String, Any>()


	abstract fun onInit()

	abstract fun cleanThreadLocals()


	open fun createLogPrinter(): YogPrinter {
		return YogDir(webDir.logDir, 15)
	}


	final override fun init(filterConfig: FilterConfig) {
		this.filterConfig = filterConfig
		sliceList.clear()
		contextPath = filterConfig.servletContext.contextPath
		val pat = this::class.findAnnotation<WebFilter>()?.urlPatterns?.toList()?.firstOrNull()
			?: throw IllegalArgumentException("urlPatterns只能设置一条, 比如: /* 或 /person/*")
		patternPath = pat.filter { it.isLetterOrDigit() || it == '_' }
		Yog.setPrinter(createLogPrinter())

		try {
			addMyRouter()
			onInit()
			for (hs in sliceList) {
				hs.onInit(this)
			}

		} catch (ex: Exception) {
			ex.printStackTrace()
		} finally {
			cleanThreadLocals()
		}
	}

	fun addSlice(hs: HttpSlice) {
		sliceList += hs
	}

	fun findRouter(block: (Router) -> Boolean): Router? {
		return routeManager.routeMap.values.firstOrNull(block)
	}

	inline fun <reified T : Annotation> firstRouter(): Router? {
		return findRouter { it.function.hasAnnotation<T>() }
	}

	final override fun destroy() {
		for (hs in sliceList) {
			hs.onDestory()
		}
		sliceList.clear()
		onDestroy()
		Yog.flush()
		Yog.clearPrinter()
		cleanThreadLocals()
	}

	open fun onDestroy() {

	}

	fun uriRes(res: String): String {
		return buildPath(contextPath, res)
	}

	fun uriAction(ac: KFunction<*>): String {
		val cls = ac.ownerClass!!
		if (cls == this::class) {
			return buildPath(contextPath, patternPath, ac.actionName)
		}
		return buildPath(contextPath, patternPath, cls.pageName, ac.actionName)
	}

	fun uriGroup(g: KClass<*>): String {
		return buildPath(contextPath, patternPath, g.pageName)
	}

	private fun addMyRouter() {
		val ls = this::class.actionList
		for (f in ls) {
			val uri = uriAction(f)
			val info = Router(uri, this::class, f, this)
			routeManager.addRouter(info)
		}
	}


	fun addGroup(vararg clses: KClass<out HttpGroup>) {
		this.routeManager.addGroup(*clses)
	}

	final override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
		try {
			if (request is HttpServletRequest && response is HttpServletResponse) {
				request.characterEncoding = "UTF-8"
				response.characterEncoding = "UTF-8"
				val c = HttpContext(this, request, response, chain)
				val r = routeManager.find(c)
				if (r == null) {
					onNextChain(request, response, chain)
				} else {
					doHttpService(c, r)
				}
			} else {
				onNextChain(request, response, chain)
			}
		} catch (ex: Exception) {
			logd(ex)
			throw  ex
		} finally {
			cleanThreadLocals()
		}
	}

	open fun onNextChain(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
		chain.doFilter(request, response)
	}

	fun doHttpService(c: HttpContext, r: Router) {
		val ls = sliceList.filter { it.match(c, r) }
		try {
			for (hs in ls) {
				hs.beforeRequest(c)
			}
			for (hs in ls) {
				if (!hs.allowRouter(c, r)) {
					return
				}
			}
			r.dispatch(c)
			for (hs in ls) {
				hs.afterRouter(c, r)
			}
			for (hs in ls) {
				hs.afterRequest(c)
			}
		} catch (ex: Exception) {
			for (a in ls) {
				if (a.processException(c, ex)) {
					return
				}
			}
			throw ex
		}
	}


	companion object {
		val pageSuffixs: HashSet<String> = hashSetOf("Group", "Page")
		const val ACTION = "Action"
		const val INDEX = "index"

	}
}


interface HttpSlice {
	fun onInit(filter: HttpFilter) {}
	fun match(context: HttpContext, router: Router): Boolean {
		return true
	}

	fun beforeRequest(context: HttpContext) {}

	fun allowRouter(context: HttpContext, router: Router): Boolean {
		return true
	}

	fun afterRouter(context: HttpContext, r: Router) {}
	fun afterRequest(context: HttpContext) {}
	fun processException(context: HttpContext, ex: Exception): Boolean {
		return false
	}

	fun onDestory() {}
}

class HttpActionManager(val filter: HttpFilter) {
	val allGroups = java.util.ArrayList<KClass<out HttpGroup>>()
	val routeMap = HashMap<String, Router>(32)

	fun onDestory() {
		routeMap.clear()
		allGroups.clear()
	}


	fun find(context: HttpContext): Router? {
		return routeMap[context.currentUri]
	}

	fun addGroup(vararg clses: KClass<out HttpGroup>) {
		allGroups.addAll(clses)
		clses.forEach { cls ->
			for (f in cls.actionList) {
				val uri = filter.uriAction(f)
				val info = Router(uri, cls, f)
				addRouter(info)
			}
		}
	}

	fun addRouter(router: Router) {
		val u = router.uri.toLowerCase()
		if (routeMap.containsKey(u)) {
			val old = routeMap[u]
			fatal("已经存在对应的Route: ${old?.function} ", u, old.toString())
		}
		routeMap[u] = router
		logd("Add Router: ", u)
	}

}

class WebDir(val filter: HttpFilter) {
	private var contextPath: String = filter.filterConfig.servletContext.contextPath

	//war解压后的目录,  WEB-INF和META-INF所在的目录
	val appDir: File = File(filter.filterConfig.servletContext.getRealPath("/"))
	private val webappsDir: File get() = appDir.parentFile

	val baseDir: File by lazy {
		sureDir("_base")
	}

	val uploadDir: File by lazy {
		sureDir("_files")
	}
	val tmpDir: File by lazy {
		sureDir("_tmp")
	}

	val logDir: File by lazy {
		sureDir("_log")
	}

	private fun sureDir(dirName: String): File {
		return File(webappsDir, contextPath.trim('/') + dirName).apply {
			if (!exists()) {
				mkdir()
			}
		}
	}
}