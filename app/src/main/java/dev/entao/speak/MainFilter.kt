package dev.entao.speak

import dev.entao.core.*
import dev.entao.sql.ConnLook
import dev.entao.core.account.TokenSlice
import dev.entao.page.modules.ResGroup
import javax.servlet.annotation.MultipartConfig
import javax.servlet.annotation.WebFilter

@MultipartConfig
@WebFilter(urlPatterns = ["/*"])
class MainFilter : HttpFilter(), HttpTimer {

	override fun cleanThreadLocals() {
		ConnLook.cleanThreadConnections()
	}


	override fun onInit() {
		addGroup(ResGroup::class, IndexGroup::class)
		addSlice(TimerSlice(this))
		addSlice(MethodAcceptor)
		addSlice(LoginCheckSlice)
		addSlice(TokenSlice("1234567890"))
	}

	override fun onHour(h: Int) {
	}

	override fun onMinute(m: Int) {
	}

	override fun onHttpTimer(dayOfMonth: Int, hour: Int, minute: Int) {

	}

}