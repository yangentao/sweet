package dev.entao.page.bootstrap

import dev.entao.page.tag.*

fun Tag.badge(vararg kv: TagAttr, block: TagBlock) {
	this.span("class" to "badge", * kv, block = block)
}

fun Tag.badge(theme: String, vararg kv: TagAttr, block: TagBlock) {
	this.span("class" to "badge"..theme, * kv, block = block)
}

fun Tag.badgeInfo(block: TagBlock) {
	this.badge("badge-info", block = block)
}

fun Tag.badgePrimary(block: TagBlock) {
	this.badge("badge-primary", block = block)
}

fun Tag.badgeSecondary(block: TagBlock) {
	this.badge("badge-secondary", block = block)
}

fun Tag.badgeSuccess(block: TagBlock) {
	this.badge("badge-success", block = block)
}

fun Tag.badgeDark(block: TagBlock) {
	this.badge("badge-dark", block = block)
}


fun Tag.badgePill(theme: String, vararg kv: TagAttr, block: TagBlock) {
	this.span("class" to "badge".."badge-pill"..theme, * kv, block = block)
}


fun Tag.badgeLink(theme: String, vararg kv: TagAttr, block: TagBlock) {
	this.a("class" to "badge"..theme, * kv, block = block)
}
