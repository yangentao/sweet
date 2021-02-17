package dev.entao.page.bootstrap

import dev.entao.base.*
import dev.entao.page.tag.*
import dev.entao.page.widget.formOptionsMap


fun Tag.propValue(p: Prop, defaultValue: String? = null): String? {
	return if (p is Prop0) {
		p.getValue()?.toString() ?: httpContext.params.str(p.userName) ?: defaultValue
	} else {
		httpContext.params.str(p.userName) ?: defaultValue
	}
}


fun Tag.formGroupRadioStatic(p: Prop, defaultValue: String? = null, inlines: Boolean = false): Tag {
	return formGroup {
		val pname = p.userName
		var selVal: String? = propValue(p, defaultValue)

		this.label { +p.userLabel }
		val ls = p.formOptionsMap
		ls.forEach { opt ->
			formCheck {
				if (inlines) {
					this classAdd "form-check-inline"
				}
				val r = radio("name" to pname, "value" to opt.key) {
					if (selVal == null) { //选中第一个
						this += "checked" to "checked"
						selVal = this["value"]
					} else if (selVal == opt.key) {
						this += "checked" to "checked"
					}
				}
				label(opt.value)["for"] = r.needId()
			}
		}
		this.processHelpText(p)
	}
}

fun Tag.formGroupCheckStatic(p: Prop, defaultValue: String? = null, inlines: Boolean = false): Tag {
	return formGroup {
		val pname = p.userName
		var selVal: String? = propValue(p, defaultValue)

		this.label { +p.userLabel }
		val ls = p.formOptionsMap
		ls.forEach { opt ->
			formCheck {
				if (inlines) {
					this classAdd "form-check-inline"
				}
				val r = checkbox("name" to pname, "value" to opt.key) {
					if (selVal == null) { //选中第一个
						this += "checked" to "checked"
						selVal = this["value"]
					} else if (selVal == opt.key) {
						this += "checked" to "checked"
					}
				}
				label(opt.value)["for"] = r.needId()
			}
		}
		this.processHelpText(p)
	}
}


