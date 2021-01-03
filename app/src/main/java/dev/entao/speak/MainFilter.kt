package dev.entao.speak

import dev.entao.sql.ConnLook
import dev.entao.core.HttpFilter
import dev.entao.page.modules.ResGroup
import javax.servlet.annotation.MultipartConfig
import javax.servlet.annotation.WebFilter

@MultipartConfig
@WebFilter(urlPatterns = ["/*"])
class MainFilter : HttpFilter() {

	override fun cleanThreadLocals() {
		ConnLook.cleanThreadConnections()
	}

	override fun createTokenPassword(): String {
		 return "1234567890"
	}

	override fun onInit() {
		addGroup(ResGroup::class, IndexGroup::class)
	}

}