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



val HttpContext.backURL: String?
	get() {
		return this.params.str(Keb.BACK_URL)?.base64Decoded
	}