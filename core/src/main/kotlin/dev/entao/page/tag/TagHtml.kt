@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.entao.page.tag

import dev.entao.core.ActionURL
import kotlin.reflect.KFunction


fun Tag.form(formAction: KFunction<*>, vararg kv: KeyValuePair, block: TagCallback): Tag {
	return form(*kv) {
		this += formAction
		this.block()
	}
}

fun Tag.form(formAction: ActionURL, vararg kv: KeyValuePair, block: TagCallback): Tag {
	return form(*kv) {
		this += formAction
		this.block()
	}
}

fun Tag.formGET(formAction: KFunction<*>, vararg kv: KeyValuePair, block: TagCallback): Tag {
	return form("method" to "GET", *kv) {
		this += formAction
		this.block()
	}
}

fun Tag.formGET(formAction: ActionURL, vararg kv: KeyValuePair, block: TagCallback): Tag {
	return form("method" to "GET", *kv) {
		this += formAction
		this.block()
	}
}

fun Tag.formPOST(formAction: KFunction<*>, vararg kv: KeyValuePair, block: TagCallback): Tag {
	return form("method" to "POST", *kv) {
		this += formAction
		this.block()
	}
}

fun Tag.formPOST(formAction: ActionURL, vararg kv: KeyValuePair, block: TagCallback): Tag {
	return form("method" to "POST", *kv) {
		this += formAction
		this.block()
	}
}
