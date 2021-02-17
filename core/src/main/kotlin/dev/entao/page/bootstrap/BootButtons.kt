@file:Suppress("unused")

package dev.entao.page.bootstrap

import dev.entao.base.firstParamName
import dev.entao.base.userLabel
import dev.entao.core.ActionURL
import dev.entao.core.HttpAction
import dev.entao.page.*
import dev.entao.page.tag.*
import kotlin.reflect.full.findAnnotation


fun Tag.a(action: HttpAction): Tag {
	return this.a {
		this += action
	}
}

fun Tag.a(action: ActionURL): Tag {
	return this.a {
		this += action
	}
}

fun Tag.a(action: HttpAction, text: String): Tag {
	return this.a {
		+text
		this += action
	}
}

fun Tag.a(action: ActionURL, text: String): Tag {
	return this.a {
		+text
		this += action
	}
}


fun Tag.linkButton(vararg kv: TagAttr, block: TagBlock): Tag {
	return a("role" to "button", "class" to "btn", *kv, block = block)
}


fun Tag.submit(text: String = "提交"): Tag {
	return this.submit("class" to "btn") { +text }
}

fun Tag.submitPrimary(text: String = "提交"): Tag {
	return this.submit("class" to "btn".."btn-primary") { +text }
}


fun Tag.buttonB(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.button("class" to "btn", *kv, block = block)
}

fun Tag.buttonTheme(theme: String, vararg kv: TagAttr, block: TagBlock): Tag {
	return this.button("class" to "btn"..theme, *kv, block = block)
}

fun Tag.buttonPrimary(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.buttonTheme("btn-primary", *kv, block = block)
}


fun Tag.buttonApplyTheme(action: HttpAction, defaultTheme: String): Tag {
	val m = action.findAnnotation<ActionTheme>()
	if (m != null) {
		if (m.value.isNotEmpty()) {
			this classAdd m.value
		}
	} else {
		if (action.findAnnotation<ActionDanger>() != null) {
			this classAdd "btn-danger"
		} else if (action.findAnnotation<ActionSafe>() != null) {
			this classAdd "btn-success"
		} else if (action.findAnnotation<ActionPrimary>() != null) {
			this classAdd "btn-primary"
		} else {
			if (defaultTheme.isNotEmpty()) {
				this classAdd defaultTheme
			}
		}
	}
	return this
}

fun Tag.buttonX(action: HttpAction, vararg kv: TagAttr, block: TagBlock = {}): Tag {
	val t = button("class" to "btn", *kv) {
		+action.userLabel
		this += action
		needId()
		this[DATA_PARAM_NAME_] = action.firstParamName ?: "id"
		this[DATA_CONFIRM_] = action.findAnnotation<FormConfirm>()?.value ?: ""
	}
	t.block()
	return t
}

fun Tag.buttonX(actionURL: ActionURL, vararg kv: TagAttr, block: TagBlock = {}): Tag {
	val action = actionURL.action
	val t = button("class" to "btn", *kv) {
		+action.userLabel
		this += actionURL
		needId()
		this[DATA_PARAM_NAME_] = action.firstParamName ?: "id"
		this[DATA_CONFIRM_] = action.findAnnotation<FormConfirm>()?.value ?: ""
	}
	t.block()
	return t
}


fun Tag.linkButtonX(action: HttpAction, vararg kv: TagAttr, block: TagBlock = {}): Tag {
	val t = linkButton(*kv) {
		this += action
	}
	t.block()
	return t
}

fun Tag.linkButtonX(actionURL: ActionURL, vararg kv: TagAttr, block: TagBlock = {}): Tag {
	val t = linkButton(*kv) {
		this += actionURL
	}
	t.block()
	return t
}


fun Tag.submitAsync(respJS: String = ""): Tag {
	val formTag = this.parent { it.tagName == "form" } ?: return this
	val formId = formTag.needId()
	script {
		"""
			$('#$formId').submit(function(e){
				e.preventDefault();
				var fm = $('#$formId');
				var data = fm.serialize();
				$.post(fm.attr('action'), data, function(resp){
						$respJS;
				});
				return false ;
			})
			"""
	}
	return this
}


fun Tag.confirm(text: String): Tag {
	this[DATA_CONFIRM_] = text
	if (this.tagName == "a") {
		this["onclick"] = "var s = $(this).attr('data-confirm');return !s || confirm(s);"
	}
	return this
}

fun Tag.confirm(action: HttpAction): Tag {
	val fcValue = action.findAnnotation<FormConfirm>()?.value ?: return this
	this[DATA_CONFIRM_] = fcValue
	if (this.tagName == "a") {
		this["onclick"] = "var s = $(this).attr('data-confirm');return !s || confirm(s);"
	}
	return this
}


fun Tag.onClickReload() {
	this["onclick"] = "reloadLink(this); return false ;"
}

fun Tag.onClickOpenDialog() {
	this["onclick"] = "openDialogPanel(this); return false;"
}
