package dev.entao.page.bootstrap

import dev.entao.base.*
import dev.entao.core.*
import dev.entao.page.*
import dev.entao.page.tag.*
import dev.entao.page.widget.uploadDiv
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

private val InputTags = setOf("input", "select", "textarea")

fun Tag.formQuery(formAction: KFunction<*>, vararg kv: KeyValuePair, block: TagCallback): Tag {
	return form("method" to "GET", DATA_FORM_QUERY_ to "1", *kv) {
		this += formAction
		this.block()
		submitPrimary("查询")
	}
}

fun Tag.formRow(block: TagCallback) {
	div("class" to "form-row", block = block)
}

fun Tag.formGroupText(labelValue: String, textValue: String): Tag {
	return formGroup {
		this.label { +labelValue }
		this.div {
			this.span("class" to "form-control-plaintext") {
				+textValue
			}
		}
	}
}

fun Tag.formGroupText(textValue: String): Tag {
	return formGroup {
		this.div {
			this.span("class" to "form-control-plaintext") {
				+textValue
			}
		}
	}
}

fun Tag.formGroupUpload(p: Prop): Tag {
	return formGroup {
		label(p.userLabel)
		this.div {
			uploadDiv(p)
			this.processHelpText(p)
		}
	}
}


fun Tag.formGroupEdit(labelText: String, editName: String, editBlock: Tag.() -> Unit = {}): Tag {
	return formGroup {
		val lb = this.label { +labelText }
		val ed = this.edit {
			this["name"] = editName
		}
		this.processGroupEditError(ed)
		ed.editBlock()
		processLabelRequire(lb, ed)
	}
}

fun Tag.formGroupEdit(p: Prop, block: TagCallback = {}): Tag {
	return formGroup {
		val lb = label(p)
		val ed = edit(p)
		this.processGroupEditError(ed)
		this.processHelpText(p)
		processLabelRequire(lb, ed)
		ed.block()
	}
}

fun Tag.formGroupTextArea(p: Prop, block: TagCallback = {}): Tag {
	return formGroup {
		val lb = label(p)
		val ed = textarea(p)
		this.processGroupEditError(ed)
		this.processHelpText(p)
		processLabelRequire(lb, ed)
		ed.block()
	}
}

fun Tag.formGroup(block: TagCallback): Tag {
	return div("class" to "form-group") {
		this.block()
		processControlCSS()
	}
}

fun Tag.edit(p: Prop): Tag {
	val v = valueByProp(p)
	val pName = p.userName
	val ed = edit("name" to pName, "id" to pName, "value" to v)
	ed.processPropertiesOfEdit(p)
	return ed
}

fun Tag.textarea(p: Prop): Tag {
	val v = valueByProp(p)
	val pName = p.userName
	return textarea("name" to pName, "id" to pName) {
		+v
		this += "rows" to (p.findAnnotation<EditRows>()?.value ?: 3).toString()
	}
}

private fun Tag.valueByProp(p: Prop): String {
	if (p !is Prop0) {
		return httpContext.httpParams.str(p.userName) ?: ""
	}

	val vv = p.getValue() ?: return httpContext.httpParams.str(p.userName) ?: ""

	if (vv is Double) {
		val kd = p.findAnnotation<NumberFormat>()
		return if (kd != null) {
			val fmt = DecimalFormat(kd.pattern)
			val n = fmt.maximumFractionDigits
			vv.maxFraction(n)
		} else {
			vv.toString()
		}
	}
	return vv.toString()

}

fun Tag.processHelpText(p: Prop) {
	val hb = p.findAnnotation<FormHelpBlock>()?.value
	if (hb != null && hb.isNotEmpty()) {
		formTextMuted(hb)
	}
}

private fun Tag.processGroupEditError(ed: Tag) {
	val er = httpContext.httpParams.str(Keb.errField(ed["name"])) ?: ""
	if (er.isNotEmpty()) {
		ed += "is-invalid"
		feedbackInvalid(er)
	}
}

private fun processLabelRequire(lb: Tag, ed: Tag) {
	lb["for"] = ed.needId()
	if (ed["required"] == "true") {
		val queryForm = lb.parent(DATA_FORM_QUERY_ to "1")
		if (queryForm == null) {
			lb.textEscaped("*")
		} else {
			ed.attrs.remove("required")
		}
	}

}


private fun Tag.processControlCSS() {
	val t = this.firstDeep { it.tagName in InputTags } ?: return
	if (t.tagName == "input") {
		if (t["type"] == "file") {
			if (!t.hasClass("form-control-file")) {
				t["class"] = "form-control-file"..t["class"]
			}
			return
		}
		if (t["readonly"] == "true") {
			if (!t.hasClass("form-control-plaintext")) {
				t["class"] = "form-control-plaintext"..t["class"]
			}
			return
		}
	}
	val tp = t["type"]
	if (tp != "radio" && tp != "checkbox") {
		if (!t.hasClass("form-control")) {
			t["class"] = "form-control"..t["class"]
		}
	}
}


private fun Tag.processPropertiesOfEdit(p: Prop) {
	if (p.isTypeInt || p.isTypeLong || p.isTypeFloat || p.isTypeDouble) {
		this += "type" to "number"
	} else if (p.isTypeClass(java.sql.Date::class)) {
		this += "type" to "date"
	}
	val stepAn = p.findAnnotation<StepValue>()
	if (stepAn == null) {
		if (p.isTypeFloat || p.isTypeDouble) {
			val keepDot = p.findAnnotation<NumberFormat>()
			if (keepDot != null && keepDot.pattern.isNotEmpty()) {
				val fmt = DecimalFormat(keepDot.pattern)
				val n = fmt.maximumFractionDigits
				if (n > 0) {
					this += "step" to 10.toDouble().pow(-n).toString()
				} else {
					this += "step" to "1"
				}
			} else {
				this += "step" to "1"
			}
		}
	}
	val anList = p.annotations
	for (an in anList) {
		when (an) {
			is FormRequired -> this += "required" to "true"
			is FormHint -> this += "placeholder" to an.value
			is FormDate -> this += "type" to "date"
			is FormPassword -> this += "type" to "password"
			is FormEmail -> this += "type" to "email"
			is FormPattern -> this += "pattern" to an.value
			is FormPhone -> {
				this += "type" to "text"
				this += "pattern" to """^1[3,4,5,6,7,8,9]\d{9}$"""
			}
			is FormTel -> {
				this += "type" to "text"
				this += "pattern" to """^(\d{3,4}\-?)?\d{6,8}$"""
			}
			is LengthRange -> {
				this += "maxlength" to an.maxValue.toString()
				this += "pattern" to ".{${an.minValue},}"
			}
			is MinLength -> {
				if (an.value > 0) {
					this += "pattern" to ".{${an.value},}"
				}
			}
			is Length -> {
				val hasRange = null != anList.find { it is LengthRange }
				if (!hasRange) {
					if (an.value > 0) {
						this += "maxlength" to an.value.toString()
					} else if (p.isTypeString) {
						this += "maxlength" to "256"
					}
				}
			}
			is MinValue -> this += "min" to an.value
			is MaxValue -> this += "max" to an.value
			is ValueRange -> {
				this += "min" to an.minVal
				this += "max" to an.maxVal
			}
			is StepValue -> this += "step" to an.value
		}
	}

}


fun Tag.formCheck(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return div("class" to "form-check", *vs) {
		this.block()
		val lb = this.first { it.tagName == "label" }
		val cb = this.children.find { it.tagName == "input" && (it["type"] == "checkbox" || it["type"] == "radio") }
		if (cb != null) {
			if (!cb.hasClass("form-check-input")) {
				cb["class"] = "form-check-input"..cb["class"]
			}
		}
		if (lb != null) {
			if (!lb.hasClass("form-check-label")) {
				lb["class"] = "form-check-label"..lb["class"]
			}
		}
		lb?.needFor(cb)
	}
}

fun Tag.feedbackInvalid(text: String) {
	if (text.isNotEmpty()) {
		div("class" to "invalid-feedback") {
			+text
		}
	}
}


fun Tag.formTextMuted(block: TagCallback): Tag {
	return tag("small", "class" to "form-text".."text-muted", block = block)
}


fun Tag.formTextMuted(text: String) {
	formTextMuted {
		+text
	}
}

