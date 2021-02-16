package dev.entao.page.bootstrap

import dev.entao.page.tag.*


fun Tag.alert(vararg kv: KeyValuePair, block: TagCallback) {
	this.div("class" to "alert".."alert-dismissible", "role" to "alert", *kv) {
		button("class" to "close", "data-dismiss" to "alert", "aria-label" to "Close") {
			span("aria-hidden" to "true") { !"&times;" }
		}
		this.block()
	}
}


fun Tag.alert(cls: String, text: String) {
	this.alert("class" to cls) {
		+text
	}
}

fun Tag.alertSuccess(text: String) {
	this.alert("alert-success", text)
}

fun Tag.alertError(text: String) {
	this.alert("alert-danger", text)
}

fun Tag.alertWanning(text: String) {
	this.alert("alert-warning", text)
}

fun Tag.alertInfo(text: String) {
	this.alert("alert-info", text)
}