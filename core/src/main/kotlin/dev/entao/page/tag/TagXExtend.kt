package dev.entao.page.tag


fun testTagX2() {
	val a = TagX("html")
	a.div("id" to "divID", "name" to "nameDiv") {
		div("id" to "upload")
	}
}

fun TagX.div(vararg attrs: TagAttrs, block: TagBlock = {}) {
	append("div", attrs, block)
}

fun TagX.span(vararg attrs: TagAttrs, block: TagBlock = {}) {
	append("span", attrs, block)
}

fun TagX.form(vararg attrs: TagAttrs, block: TagBlock = {}) {
	append("form", attrs, block)
}

fun TagX.title(vararg attrs: TagAttrs, block: TagBlock = {}) {
	append("title", attrs, block)
}

