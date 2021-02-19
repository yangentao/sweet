package dev.entao.core

import javax.servlet.FilterConfig

interface HttpSlice {
	fun onInit(filter: HttpFilter, config: FilterConfig) {}
	fun match(context: HttpContext, router: Router): Boolean {
		return true
	}

	fun beforeRequest(context: HttpContext) {}

	fun acceptRouter(context: HttpContext, router: Router): Boolean {
		return true
	}

	fun afterRouter(context: HttpContext, r: Router) {}
	fun afterRequest(context: HttpContext) {}
	fun processError(context: HttpContext, ex: Exception): Boolean {
		return false
	}

	fun onDestory() {}
}