package dev.entao.page.bootstrap

import dev.entao.page.tag.*


fun Tag.progress(vararg kv: KeyValuePair, block: Tag.() -> Unit): Tag {
	return this.div("class" to "progress", *kv) {
		this.block()
	}
}

fun Tag.progressBar(block: Tag.() -> Unit): Tag {
	return this.div("class" to "progress-bar", "role" to V.progressbar) {
		this["aria-valuemin"] = "0"
		this["aria-valuemax"] = "100"
		this.block()
	}
}

