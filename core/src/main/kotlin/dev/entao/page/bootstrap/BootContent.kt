package dev.entao.page.bootstrap

import dev.entao.page.tag.*

fun Tag.blockquote(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("blockquote", "class" to "blockquote", *kv, block = block)
}



fun Tag.blockquoteFooter(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.tag("footer", "class" to "blockquote-footer", *kv, block = block)
}