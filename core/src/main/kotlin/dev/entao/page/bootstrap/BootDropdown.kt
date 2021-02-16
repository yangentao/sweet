package dev.entao.page.bootstrap

import dev.entao.page.tag.*

fun Tag.dropdownDiv(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return div("class" to "dropdown", *kv, block = block)
}

fun Tag.dropdownToggleButton(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return buttonB("class" to "dropdown-toggle",
		"data-toggle" to "dropdown", "aria-haspopup" to "true", "aria-expanded" to "false",
			*kv, block = block)

}


fun Tag.dropdownMenu(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return div("class" to "dropdown-menu", *kv, block = block)
}

fun Tag.dropdownMenuRight(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return div("class" to "dropdown-menu".."dropdown-menu-right", *kv, block = block)
}

fun Tag.dropdownItem(vararg kv: KeyValuePair, block: TagCallback): Tag {
	return a("class" to "dropdown-item", *kv, block = block)
}