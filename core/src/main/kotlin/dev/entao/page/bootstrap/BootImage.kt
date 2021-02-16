package dev.entao.page.bootstrap

import dev.entao.page.tag.*

fun Tag.imgFluid(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("img", "class" to "img-fluid", *kv, block = block)
}

fun Tag.imgThumbnail(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("img", "class" to "img-thumbnail", *kv, block = block)
}

fun Tag.figure(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("figure", "class" to "figure", *kv, block = block)
}

fun Tag.figureCaption(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("figcaption", "class" to "figure-caption", *kv, block = block)
}

fun Tag.figureImage(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.tag("img", "class" to "figure-img".."img-fluid".."rounded", *kv, block = block)
}