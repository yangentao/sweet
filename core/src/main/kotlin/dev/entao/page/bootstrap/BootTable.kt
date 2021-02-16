package dev.entao.page.bootstrap

import dev.entao.page.tag.*


fun Tag.tableNormal(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.table("class" to "table", *kv, block = block)
}

fun Tag.tableDark(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.table("class" to "table".."thead-dark", *kv, block = block)
}

fun Tag.theadDark(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.thead("class" to "thead-dark", *kv, block = block)
}

fun Tag.theadLight(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.thead("class" to "thead-light", *kv, block = block)
}

fun Tag.tableResponsive(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "table-responsive", *kv, block = block)
}
