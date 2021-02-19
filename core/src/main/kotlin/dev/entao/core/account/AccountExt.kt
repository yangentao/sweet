package dev.entao.core.account

import dev.entao.base.base64Decoded
import dev.entao.base.base64Encoded
import dev.entao.core.*
import javax.servlet.FilterConfig
import kotlin.reflect.full.hasAnnotation

//web登录成功后, 设置account和accountName

var HttpContext.accountId: Long
	get() {
		return getSession(Keb.ACCOUNT_ID)?.toLongOrNull() ?: 0L
	}
	set(value) {
		if (value == 0L) {
			this.removeSession(Keb.ACCOUNT_ID)
		} else {
			putSession(Keb.ACCOUNT_ID, value.toString())
		}
	}
var HttpContext.account: String
	get() {
		return getSession(Keb.ACCOUNT) ?: ""
	}
	set(value) {
		if (value.isEmpty()) {
			this.removeSession(Keb.ACCOUNT)
		} else {
			putSession(Keb.ACCOUNT, value)
		}
	}

var HttpContext.accountName: String
	get() {
		val a = this.account
		if (a.isEmpty()) {
			return ""
		}
		return getSession(Keb.ACCOUNT_NAME) ?: a
	}
	set(value) {
		putSession(Keb.ACCOUNT_NAME, value)
	}

fun HttpContext.clearAccountInfo() {
	this.accountId = 0L
	this.account = ""
	this.accountName = ""
}

val HttpContext.isAccountLogined: Boolean
	get() {
		return account.isNotEmpty()
	}


object LoginCheckSlice : HttpSlice {
	lateinit var filter: HttpFilter
	val loginUri: String by lazy {
		filter.findRouter { it.function.hasAnnotation<LoginAction>() }?.uri?.toLowerCase() ?: ""
	}

	override fun onInit(filter: HttpFilter, config: FilterConfig) {
		super.onInit(filter, config)
		this.filter = filter
	}

	override fun match(context: HttpContext, router: Router): Boolean {
		return router.needLogin
	}

	override fun acceptRouter(context: HttpContext, router: Router): Boolean {
		if (!context.isAccountLogined) {
			if (loginUri.isNotEmpty()) {
				if (context.acceptHtml) {
					var url = context.request.requestURI
					val qs = context.request.queryString ?: ""
					if (qs.isNotEmpty()) {
						url = "$url?$qs"
					}
					url = url.base64Encoded
					val u = Url(loginUri)
					u.replace(Keb.BACK_URL, url)
					context.redirect(u.build())
					return false
				}
			}
			context.abort(401)
			return false
		}
		return true
	}
}

val HttpContext.backURL: String?
	get() {
		return this.params.str(Keb.BACK_URL)?.base64Decoded
	}