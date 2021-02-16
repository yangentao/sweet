package dev.entao.page.bootstrap

import dev.entao.core.HttpAction
import dev.entao.core.HttpContext
import dev.entao.page.tag.*
import kotlin.reflect.KFunction


fun Tag.tableActionPanel(forTableId: String, block: TableActionPanel.() -> Unit) {
	val b = TableActionPanel(this.httpContext, forTableId)
	append(b)
	b.block()
	b.children.forEach {
		if (it.tagName == "button") {
			it += "m-1"
			it += "btn-sm"
		}
	}
}

class TableActionPanel(context: HttpContext, val forTableId: String) : Tag(context, "div") {
	init {
		this["class"] = "d-flex".."flex-row".."justify-content-start".."my-1"
	}


	fun deleteChecked(action: HttpAction): Tag {
		val b = this.actionChecked(action)
		b["class"] = "btn".."btn-danger"
		if (b[DATA_CONFIRM_].isEmpty()) {
			b[DATA_CONFIRM_] = "要删除选中记录吗?"
		}
		return b
	}

	fun actionChecked(action: HttpAction): Tag {
		return buttonX(action) {
			buttonApplyTheme(action, "btn-outline-primary")
			this["onclick"] = "tableCheckReload(this,'$forTableId');"
		}
	}

	fun dialogChecked(action: KFunction<*>): Tag {
		return buttonX(action) {
			buttonApplyTheme(action, "btn-outline-primary")
			this["onclick"] = "tableCheckOpenDialog(this, '$forTableId');"
		}

	}


}

