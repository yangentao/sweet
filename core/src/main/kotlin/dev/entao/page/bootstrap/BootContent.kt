package dev.entao.page.bootstrap

import dev.entao.page.tag.*

fun Tag.blockquote(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("blockquote", "class" to "blockquote", *kv, block = block)
}



fun Tag.blockquoteFooter(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("footer", "class" to "blockquote-footer", *kv, block = block)
}