@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.entao.core

import dev.entao.base.ownerClass
import dev.entao.log.Yog
import dev.entao.log.YogDir
import dev.entao.log.YogPrinter
import dev.entao.log.logd
import dev.entao.core.account.LoginCheckSlice
import dev.entao.core.account.TokenSlice
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

	open var loginUri: String = ""
	open var logoutUri: String = ""
	open var accountInfoUri: String = ""

	open var appName: String = "主页"

	private lateinit var filterConfig: FilterConfig
	var contextPath: String = ""
		private set

	// /* => "" , /person/*  => person     @WebFilter中的urlPatterns
	var patternPath: String = ""
		private set

	val webDir = WebDir()

	val routeManager: HttpActionManager by lazy {
		HttpActionManager(this)
	}
	val sliceList: ArrayList<HttpSlice> = ArrayList()

	val infoMap = HashMap<String, Any>()

	fun addSlice(hs: HttpSlice) {
		sliceList += hs
	}

	abstract fun onInit()

	abstract fun cleanThreadLocals()

	abstract fun createTokenPassword(): String

	open fun createLogPrinter(): YogPrinter {
		return YogDir(webDir.logDir, 15)
	}

	open fun onDestroy() {

	}

	final override fun init(filterConfig: FilterConfig) {
		this.filterConfig = filterConfig
		sliceList.clear()
		contextPath = filterConfig.servletContext.contextPath
		val pat = this::class.findAnnotation<WebFilter>()?.urlPatterns?.toList()?.firstOrNull()
			?: throw IllegalArgumentException("urlPatterns只能设置一条, 比如: /* 或 /person/*")
		patternPath = pat.filter { it.isLetterOrDigit() || it == '_' }

		webDir.onConfig(this, filterConfig)
		Yog.setPrinter(createLogPrinter())
		logd("Server Start!")


		try {
			addRouterOfThis()
			onInit()
			for (hs in sliceList) {
				hs.onInit(this, filterConfig)
			}

			if (this.loginUri.isEmpty()) {
				this.loginUri = findRouter { it.function.hasAnnotation<LoginAction>() }?.uri?.toLowerCase() ?: ""
			}
			if (this.logoutUri.isEmpty()) {
				this.logoutUri = findRouter { it.function.hasAnnotation<LogoutAction>() }?.uri?.toLowerCase() ?: ""
			}
			if (this.accountInfoUri.isEmpty()) {
				this.accountInfoUri = findRouter { it.function.hasAnnotation<AccountInfoAction>() }?.uri?.toLowerCase() ?: ""
			}
		} catch (ex: Exception) {
			ex.printStackTrace()
		} finally {
			cleanThreadLocals()
		}
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

	fun resUri(res: String): String {
		return buildPath(contextPath, res)
	}

	fun actionUri(ac: KFunction<*>): String {
		val cls = ac.ownerClass!!
		if (cls == this::class) {
			return buildPath(contextPath, patternPath, ac.actionName)
		}
		return buildPath(contextPath, patternPath, cls.pageName, ac.actionName)
	}

	fun groupUri(g: KClass<*>): String {
		return buildPath(contextPath, patternPath, g.pageName)
	}

	private fun addRouterOfThis() {
		val ls = this::class.actionList
		for (f in ls) {
			val uri = actionUri(f)
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
					chain.doFilter(request, response)
				} else {
					doHttpService(c, r)
				}
			} else {
				chain.doFilter(request, response)
			}
		} catch (ex: Exception) {
			logd(ex)
			throw  ex
		} finally {
			cleanThreadLocals()
		}
	}

	fun doHttpService(c: HttpContext, r: Router) {
		val ls = sliceList.filter { it.match(c, r) }
		try {
			for (hs in ls) {
				hs.beforeRequest(c)
			}
			for (hs in ls) {
				if (!hs.acceptRouter(c, r)) {
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
				if (a.processError(c, ex)) {
					return
				}
			}
			throw ex
		}
	}


	val navControlerList: List<Pair<String, KClass<*>>> by lazy {
		val navConList = ArrayList<Pair<String, KClass<*>>>()
//		for (c in allPages) {
//			val ni = c.findAnnotation<NavItem>()
//			if (ni != null) {
//				val lb = if (ni.group.isNotEmpty()) {
//					ni.group
//				} else {
//					c.userLabel
//				}
//				navConList.add(lb to c)
//			}
//		}
		navConList
	}

	companion object {
		val pageSuffixs: HashSet<String> = hashSetOf("Group", "Page")
		const val ACTION = "Action"
		const val INDEX = "index"

	}
}