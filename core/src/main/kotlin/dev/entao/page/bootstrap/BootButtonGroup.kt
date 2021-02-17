package dev.entao.page.bootstrap

import dev.entao.page.tag.*

//<div class="btn-group" role="group" aria-label="Basic example">
//	<button type="button" class="btn btn-secondary">Left</button>
//	<button type="button" class="btn btn-secondary">Middle</button>
//	<button type="button" class="btn btn-secondary">Right</button>
//</div>
fun Tag.buttonGroup(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.div("role" to "group", "class" to "btn-group", *kv, block = block)
}

fun Tag.buttonGroup(theme: String, vararg kv: TagAttr, block: TagBlock): Tag {
	val g = this.div("role" to "group", "class" to "btn-group", *kv, block = block)
	g[TAGNAME_ to "button"].forEach {
		it["class"] = "btn"..theme
	}
	return g
}

//<div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
//	<div class="btn-group mr-2" role="group" aria-label="First group">
//		<button type="button" class="btn btn-secondary">1</button>
//		<button type="button" class="btn btn-secondary">2</button>
//		<button type="button" class="btn btn-secondary">3</button>
//		<button type="button" class="btn btn-secondary">4</button>
//	</div>
//	<div class="btn-group mr-2" role="group" aria-label="Second group">
//		<button type="button" class="btn btn-secondary">5</button>
//		<button type="button" class="btn btn-secondary">6</button>
//		<button type="button" class="btn btn-secondary">7</button>
//	</div>
//	<div class="btn-group" role="group" aria-label="Third group">
//		<button type="button" class="btn btn-secondary">8</button>
//	</div>
//</div>
fun Tag.buttonToolbar(vararg kv: TagAttr, block: TagBlock): Tag {
	return this.div("role" to "toolbar", "class" to "btn-toolbar", *kv, block = block)
}

//<div class="btn-group" role="group">
//	<button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
//		Dropdown
//	</button>
//	<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
//		<a class="dropdown-item" href="#">Dropdown link</a>
//		<a class="dropdown-item" href="#">Dropdown link</a>
//	</div>
//</div>
fun Tag.buttonGroupDropdown(btnText: String, size: Int, block: (Tag, Int) -> Unit): Tag {
	return this.buttonGroup() {
		button("class" to "btn-secondary".."dropdown-toggle") {
			+btnText
		}
		div("class" to "dropdown-menu") {
			for (i in 0 until size) {
				a("class" to "dropdown-item", "href" to "#") {
					block(this, i)
				}
			}
		}
	}
}