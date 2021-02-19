@file:Suppress("unused")

package dev.entao.page.tag


fun Tag.a(vararg kv: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("a", *kv, block = block)
}

fun Tag.button(vararg kv: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("button", "type" to "button", *kv, block = block)
}

fun Tag.submit(vararg kv: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("button", "type" to "submit", *kv, block = block)
}

fun Tag.link(vararg kv: TagAttr): Tag {
	return this.tag("link", *kv, block = {})
}

fun Tag.meta(vararg kv: TagAttr): Tag {
	return this.tag("meta", *kv, block = {})
}

fun Tag.keywords(ws: List<String>) {
	meta("name" to "keywords", "content" to ws.joinToString(","))
}

fun Tag.scriptRes(srcRes: String) {
	this.tag("script", "type" to "text/javascript", "src" to httpContext.uriRes(srcRes))
}

fun Tag.script(src: String) {
	this.tag("script", "type" to "text/javascript", "src" to src)
}

fun Tag.script(block: () -> String) {
	this.tag("script", "type" to "text/javascript") {
		textUnsafe(block)
	}
}

fun Tag.option(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("option", *kv, block = block)
}

fun Tag.div(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("div", *kv, block = block)
}

fun Tag.i(vararg kv: TagAttr, block: TagBlock? = null): Tag {
	return this.tag("i", *kv, block = block)
}

fun Tag.form(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("form", *kv, block = block)
}

fun Tag.label(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("label", *kv, block = block)
}

fun Tag.img(vararg kv: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("img", *kv, block = block)
}

fun Tag.span(vararg kv: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("span", *kv, block = block)
}

fun Tag.hr(vararg kv: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("hr", *kv, block = block)
}

fun Tag.pre(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("pre", *kv, block = block)
}

fun Tag.code(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("code", *kv, block = block)
}

fun Tag.var_(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("var", *kv, block = block)
}

fun Tag.kbd(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("kbd", *kv, block = block)
}

fun Tag.ol(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("ol", *kv, block = block)
}

fun Tag.ul(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("ul", *kv, block = block)
}

fun Tag.li(vararg kv: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("li", *kv, block = block)
}

fun Tag.h1(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("h1", *kv, block = block)
}

fun Tag.h2(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("h2", *kv, block = block)
}

fun Tag.h3(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("h3", *kv, block = block)
}

fun Tag.h4(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("h4", *kv, block = block)
}

fun Tag.h5(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("h5", *kv, block = block)
}

fun Tag.h6(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("h6", *kv, block = block)
}

fun Tag.p(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("p", *kv, block = block)
}

fun Tag.dl(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("dl", *kv, block = block)
}

fun Tag.dt(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("dt", *kv, block = block)
}

fun Tag.dd(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("dd", *kv, block = block)
}

fun Tag.table(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("table", *kv, block = block)
}

fun Tag.thead(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("thead", *kv, block = block)
}


fun Tag.tbody(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("tbody", *kv, block = block)
}


fun Tag.th(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("th", *kv, block = block)
}


fun Tag.tr(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("tr", *kv, block = block)
}


fun Tag.td(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("td", *kv, block = block)
}


fun Tag.col(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("col", *kv, block = block)
}


fun Tag.colgroup(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("colgroup", *kv, block = block)
}


fun Tag.well(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("well", *kv, block = block)
}


fun Tag.strong(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("strong", *kv, block = block)
}


fun Tag.small(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("small", *kv, block = block)
}


fun Tag.nav(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("nav", *kv, block = block)
}


fun Tag.font(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("font", *kv, block = block)
}


fun Tag.font(size: Int, color: String, block: TagBlock) {
	this.font("size" to size.toString(), "color" to color, block = block)
}


fun Tag.textarea(vararg vs: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("textarea", "rows" to "3", *vs, block = block)
}


fun Tag.input(vararg vs: TagAttr, block: TagBlock = {}): Tag {
	return this.tag("input", *vs, block = block)
}


fun Tag.edit(vararg vs: TagAttr, block: TagBlock = {}): Tag {
	return input("type" to "text", *vs, block = block)
}


fun Tag.date(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "date", *vs, block = block)
}

fun Tag.time(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "time", *vs, block = block)
}

fun Tag.datetime(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "datetime", *vs, block = block)
}

fun Tag.file(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "file", *vs, block = block)
}

fun Tag.password(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "password", *vs, block = block)
}

fun Tag.email(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "email", *vs, block = block)
}

fun Tag.hidden(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "hidden", *vs, block = block)
}

fun Tag.radio(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "radio", *vs, block = block)
}

fun Tag.checkbox(vararg vs: TagAttr, block: TagBlock): Tag {
	return input("type" to "checkbox", *vs, block = block)
}

fun Tag.footer(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("footer", *kv, block = block)
}


fun Tag.article(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("article", *kv, block = block)
}

fun Tag.stylesheet(url: String) {
	link("rel" to "stylesheet", "href" to url)
}

fun Tag.stylesheetRes(url: String) {
	link("rel" to "stylesheet", "href" to httpContext.uriRes(url))
}