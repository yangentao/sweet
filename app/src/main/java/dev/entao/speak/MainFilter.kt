package dev.entao.speak

import dev.entao.sql.ConnLook
import dev.entao.core.HttpFilter
import dev.entao.core.HttpTimer
import dev.entao.core.MethodAcceptor
import dev.entao.core.TimerSlice
import dev.entao.core.account.LoginCheckSlice
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

	override fun createTokenPassword(): String {
		return "1234567890"
	}

	override fun onInit() {
		addGroup(ResGroup::class, IndexGroup::class)
		addSlice(TimerSlice(this))
		addSlice(MethodAcceptor)
		addSlice(LoginCheckSlice)
		addSlice(TokenSlice(createTokenPassword()))
	}

	override fun onHour(h: Int) {
	}

	override fun onMinute(m: Int) {
	}

	override fun onHttpTimer(dayOfMonth: Int, hour: Int, minute: Int) {

	}

}