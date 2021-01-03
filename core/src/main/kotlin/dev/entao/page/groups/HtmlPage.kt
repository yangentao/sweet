package dev.entao.page.groups

import dev.entao.core.HttpContext
import dev.entao.core.HttpScope
import dev.entao.page.tag.*

open class HtmlPage(final override val context: HttpContext) : HttpScope {
	val html = Tag(context, "html")

	init {
		html.tag("head") {}
		html.tag("body") {}
	}

	val head: Tag get() = html.head
	val body: Tag get() = html.body

	fun head(block: TagCallback) {
		this.head.block()
	}

	fun body(block: TagCallback) {
		this.body.block()
	}

	fun title(s: String) {
		val a = head.single("title")
		a.children.clear()
		a.textEscaped(s)
	}

	fun send() {
		context.sendHtmlTag(html)
	}


}

val Tag.head: Tag get() = this.root.single("head")

val Tag.body: Tag get() = this.root.single("body")

