@file:Suppress("unused")

package dev.entao.page.bootstrap

import dev.entao.page.tag.*


val _colx = "col-12".."col-md"
val _colx_1 = "col-12".."col-md-1"
val _colx_2 = "col-12".."col-md-2"
val _colx_3 = "col-12".."col-md-3"
val _colx_4 = "col-12".."col-md-4"
val _colx_5 = "col-12".."col-md-5"
val _colx_6 = "col-12".."col-md-6"
val _colx_7 = "col-12".."col-md-7"
val _colx_8 = "col-12".."col-md-8"
val _colx_9 = "col-12".."col-md-9"
val _colx_10 = "col-12".."col-md-10"
val _colx_11 = "col-12".."col-md-11"
val _colx_12 = "col-12".."col-md-12"

fun Tag.divContainer(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "container", *kv, block = block)
}

fun Tag.divContainerFluid(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "container-fluid", *kv, block = block)
}

fun Tag.divRow(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "row", * kv, block = block)
}

fun Tag.divCol(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col", * kv, block = block)
}


fun Tag.divCol1(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-1", *kv, block = block)
}

fun Tag.divCol2(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-2", *kv, block = block)
}

fun Tag.divCol3(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-3", *kv, block = block)
}

fun Tag.divCol4(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-4", *kv, block = block)
}

fun Tag.divCol5(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-5", *kv, block = block)
}

fun Tag.divCol6(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-6", *kv, block = block)
}

fun Tag.divCol7(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-7", *kv, block = block)
}

fun Tag.divCol8(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-8", *kv, block = block)
}

fun Tag.divCol9(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-9", *kv, block = block)
}

fun Tag.divCol10(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-10", *kv, block = block)
}

fun Tag.divCol11(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-11", *kv, block = block)
}

fun Tag.divCol12(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "col-12", *kv, block = block)
}


fun Tag.row(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "row", * kv, block = block)
}

fun Tag.rowCenter(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to "row".."justify-content-center", * kv, block = block)
}

fun Tag.colX(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx, *kv, block = block)
}

fun Tag.colXN(n: Int, vararg kv: KeyValuePair, block: TagCallback): Tag {
	assert(n in 1..12)
	return this.div("class" to "col-12".."col-md-$n", *kv, block = block)
}

fun Tag.colX1(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_1, *kv, block = block)
}

fun Tag.colX2(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_2, *kv, block = block)
}

fun Tag.colX3(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_3, *kv, block = block)
}

fun Tag.colX4(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_4, *kv, block = block)
}

fun Tag.colX5(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_5, *kv, block = block)
}

fun Tag.colX6(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_6, *kv, block = block)
}


fun Tag.colX7(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_7, *kv, block = block)
}

fun Tag.colX8(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_8, *kv, block = block)
}

fun Tag.colX9(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_9, *kv, block = block)
}

fun Tag.colX10(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_10, *kv, block = block)
}

fun Tag.colX11(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_11, *kv, block = block)
}

fun Tag.colX12(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return this.div("class" to _colx_12, *kv, block = block)
}