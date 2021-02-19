package dev.entao.core

import dev.entao.base.Prop1
import dev.entao.base.isTypeInt
import dev.entao.base.isTypeLong
import dev.entao.base.isTypeString


interface HttpScope {
	val context: HttpContext
	val httpParams: HttpParams get() = context.params

	val HttpAction.uri: String
		get() {
			return context.filter.uriAction(this)
		}

	fun redirect(action: HttpAction) {
		context.redirect(action.uri)
	}

	fun redirect(actionUrl: ActionURL) {
		context.redirect(actionUrl.toURL(context))
	}


	fun resUri(file: String): String {
		return context.resUri(file)
	}


	fun paramValue(p: Prop1): Any? {
		if (p.isTypeInt) {
			return context.params.int(p)
		}
		if (p.isTypeLong) {
			return context.params.long(p)
		}
		if (p.isTypeString) {
			return context.params.str(p)
		}
		return null
	}

}