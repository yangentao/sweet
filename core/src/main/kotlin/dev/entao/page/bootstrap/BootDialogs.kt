package dev.entao.page.bootstrap

import dev.entao.core.HttpContext
import dev.entao.core.HttpScope
import dev.entao.page.tag.*

fun installDialogs(tag: Tag) {
	tag.apply {
		div("id" to "dialogPanel") {

		}
		div("id" to "confirmDlgPanel") {
			val b = ModalDialog(httpContext)
			b.modalTitleText("确认")
			b.modalBody {
				p {
					+"确认内容"
				}
			}
			b.modalFooter {
				buttonPrimary {
					+"确定"
				}
			}
			b.closeText("取消")
			append(b)
		}
		div("id" to "alertDlgPanel") {
			val b = ModalDialog(httpContext)
			b.modalTitleText("提示")
			b.modalBody {
				p {
					+"提示内容"
				}
			}
			b.closeText("关闭")
			append(b)
		}
	}
}


class ModalDialog(context: HttpContext) : Tag(context, "div") {

	lateinit var modalHeader: Tag
	lateinit var modalBody: Tag
	lateinit var modalFooter: Tag
	lateinit var modalHeaderTitle: Tag
	lateinit var closeButton: Tag

	init {
		this classAdd "modal"
		this["tabindex"] = "-1"
		this["role"] = "dialog"
		div("class" to "modal-dialog".."modal-dialog-centered", "role" to "document") {
			div("class" to "modal-content") {
				modalHeader = div("class" to "modal-header") {
					modalHeaderTitle = h5("class" to "modal-title") {
					}
					buttonB("class" to "close", "data-dismiss" to "modal") {
						span {
							textUnsafe("&times;")
						}
					}
				}
				modalBody = div("class" to "modal-body") {
				}
				modalFooter = div("class" to "modal-footer") {
					closeButton = buttonB("class" to "btn-secondary", "data-dismiss" to "modal") {
						+"关闭"
					}
				}
			}
		}

	}

	fun closeText(text: String) {
		val a = closeButton.children.first() as TextEscaped
		a.text = text
	}

	fun modalTitleText(titleText: String) {
		modalHeaderTitle.textEscaped(titleText)
	}

	fun modalBody(block: TagBlock) {
		modalBody.block()
	}

	fun modalFooter(block: TagBlock) {
		modalFooter.block()
	}
}

fun Tag.modalDialog(block: ModalDialog.() -> Unit) {
	val d = ModalDialog(this.httpContext)
	append(d)
	d.block()
}

fun HttpScope.dialogAlert(title: String, msg: String) {
	val d = ModalDialog(context)
	d.modalTitleText(title)
	d.modalBody {
		p {
			textEscaped(msg)
		}
	}
	d.closeText("确定")
	context.sendHtmlTag(d)
}

fun HttpScope.dialogForm(title: String, bodyBlock: Tag.() -> Unit) {
	val d = ModalDialog(context)
	d.modalTitleText(title)
	d.modalBody(bodyBlock)
	d.closeText("取消")
	d.modalFooter {
		buttonPrimary("class" to "m-1") {
			+"提交"
			this["onclick"] = "dialogSubmitReload(this);"
		}
	}
	context.sendHtmlTag(d)
}

