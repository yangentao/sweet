@file:Suppress("unused")

package dev.entao.page.tag


fun Tag.a(vararg kv: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("a", *kv, block = block)
}

fun Tag.button(vararg kv: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("button", "type" to "button", *kv, block = block)
}

fun Tag.submit(vararg kv: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("button", "type" to "submit", *kv, block = block)
}

fun Tag.link(vararg kv: KeyValuePair): Tag {
	return this.tag("link", *kv, block = {})
}

fun Tag.meta(vararg kv: KeyValuePair): Tag {
	return this.tag("meta", *kv, block = {})
}

fun Tag.keywords(ws: List<String>) {
	meta("name" to "keywords", "content" to ws.joinToString(","))
}

fun Tag.scriptRes(srcRes: String) {
	this.tag("script", "type" to "text/javascript", "src" to httpContext.resUri(srcRes))
}

fun Tag.script(src: String) {
	this.tag("script", "type" to "text/javascript", "src" to src)
}

fun Tag.script(block: () -> String) {
	this.tag("script", "type" to "text/javascript") {
		textUnsafe(block)
	}
}

fun Tag.option(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("option", *kv, block = block)
}

fun Tag.div(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("div", *kv, block = block)
}

fun Tag.i(vararg kv: KeyValuePair, block: TagCallback? = null): Tag {
	return this.tag("i", *kv, block = block)
}

fun Tag.form(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("form", *kv, block = block)
}

fun Tag.label(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("label", *kv, block = block)
}

fun Tag.img(vararg kv: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("img", *kv, block = block)
}

fun Tag.span(vararg kv: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("span", *kv, block = block)
}

fun Tag.hr(vararg kv: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("hr", *kv, block = block)
}

fun Tag.pre(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("pre", *kv, block = block)
}

fun Tag.code(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("code", *kv, block = block)
}

fun Tag.var_(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("var", *kv, block = block)
}

fun Tag.kbd(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("kbd", *kv, block = block)
}

fun Tag.ol(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("ol", *kv, block = block)
}

fun Tag.ul(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("ul", *kv, block = block)
}

fun Tag.li(vararg kv: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("li", *kv, block = block)
}

fun Tag.h1(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("h1", *kv, block = block)
}

fun Tag.h2(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("h2", *kv, block = block)
}

fun Tag.h3(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("h3", *kv, block = block)
}

fun Tag.h4(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("h4", *kv, block = block)
}

fun Tag.h5(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("h5", *kv, block = block)
}

fun Tag.h6(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("h6", *kv, block = block)
}

fun Tag.p(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("p", *kv, block = block)
}

fun Tag.dl(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("dl", *kv, block = block)
}

fun Tag.dt(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("dt", *kv, block = block)
}

fun Tag.dd(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("dd", *kv, block = block)
}

fun Tag.table(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("table", *kv, block = block)
}

fun Tag.thead(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("thead", *kv, block = block)
}


fun Tag.tbody(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("tbody", *kv, block = block)
}


fun Tag.th(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("th", *kv, block = block)
}


fun Tag.tr(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("tr", *kv, block = block)
}


fun Tag.td(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("td", *kv, block = block)
}


fun Tag.col(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("col", *kv, block = block)
}


fun Tag.colgroup(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("colgroup", *kv, block = block)
}


fun Tag.well(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("well", *kv, block = block)
}


fun Tag.strong(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("strong", *kv, block = block)
}


fun Tag.small(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("small", *kv, block = block)
}


fun Tag.nav(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("nav", *kv, block = block)
}


fun Tag.font(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("font", *kv, block = block)
}


fun Tag.font(size: Int, color: String, block: TagCallback) {
	this.font("size" to size.toString(), "color" to color, block = block)
}


fun Tag.textarea(vararg vs: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("textarea", "rows" to "3", *vs, block = block)
}


fun Tag.input(vararg vs: KeyValuePair, block: TagCallback = {}): Tag {
	return this.tag("input", *vs, block = block)
}


fun Tag.edit(vararg vs: KeyValuePair, block: TagCallback = {}): Tag {
	return input("type" to "text", *vs, block = block)
}


fun Tag.date(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "date", *vs, block = block)
}

fun Tag.time(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "time", *vs, block = block)
}

fun Tag.datetime(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "datetime", *vs, block = block)
}

fun Tag.file(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "file", *vs, block = block)
}

fun Tag.password(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "password", *vs, block = block)
}

fun Tag.email(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "email", *vs, block = block)
}

fun Tag.hidden(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "hidden", *vs, block = block)
}

fun Tag.radio(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "radio", *vs, block = block)
}

fun Tag.checkbox(vararg vs: KeyValuePair, block: TagCallback): Tag {
	return input("type" to "checkbox", *vs, block = block)
}

fun Tag.footer(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("footer", *kv, block = block)
}


fun Tag.article(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("article", *kv, block = block)
}

fun Tag.stylesheet(url: String) {
	link("rel" to "stylesheet", "href" to url)
}

fun Tag.stylesheetRes(url: String) {
	link("rel" to "stylesheet", "href" to httpContext.resUri(url))
}