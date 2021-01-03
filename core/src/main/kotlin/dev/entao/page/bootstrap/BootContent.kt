package dev.entao.page.bootstrap

import dev.entao.page.tag.*

fun Tag.blockquote(vararg kv: HKeyValue, block: TagCallback): Tag {
	return this.tag("blockquote", class_ to _blockquote, *kv, block = block)
}



fun Tag.blockquoteFooter(vararg kv: HKeyValue, block: TagCallback): Tag {
	return this.tag("footer", class_ to _blockquote_footer, *kv, block = block)
}