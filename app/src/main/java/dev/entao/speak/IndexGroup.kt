package dev.entao.speak

import dev.entao.base.Label
import dev.entao.core.HttpContext
import dev.entao.core.HttpGroup
import dev.entao.core.LoginAction

@Label("员工")
class IndexGroup(context: HttpContext) : HttpGroup(context) {




	@LoginAction
	fun loginAction() {
		resultSender.ok()
	}



}