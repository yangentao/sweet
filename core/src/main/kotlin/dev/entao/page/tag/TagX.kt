@file:Suppress("MemberVisibilityCanBePrivate")

package dev.entao.page.tag

import dev.entao.core.HttpContext


typealias TagBlock = TagX.() -> Unit
typealias TagAttrs = Pair<String, String>

class TagX(val tagName: String, vararg attrs: TagAttrs) {
	val attrMap: AttrMap = AttrMap()
	val children: ArrayList<TagX> = ArrayList()
	var parent: TagX? = null
	var _context: HttpContext? = null

	init {
		for (p in attrs) {
			attrMap[p.first] = p.second
		}
	}

	val root: TagX
		get() {
			var t: TagX = this
			while (t.parent != null) {
				t = t.parent!!
			}
			return t
		}
	val context: HttpContext
		get() {
			var t: TagX? = this
			while (t != null) {
				if (t._context != null) {
					this._context = t._context
					return t._context!!
				}
				t = t.parent
			}
			error("No HttpContext")
		}

	operator fun get(index: Int): TagX {
		return children[index]
	}

	operator fun get(attr: String): String {
		return attrMap[attr] ?: ""
	}

	operator fun set(attr: String, value: String?) {
		if (value == null) {
			attrMap.remove(attr)
		} else {
			attrMap[attr] = value
		}
	}

	fun removeAttr(attr: String) {
		attrMap.remove(attr)
	}

	fun removeAt(index: Int): TagX {
		return children.removeAt(index)
	}

	fun append(tag: TagX, block: TagBlock = {}) {
		children += tag
		tag.parent = this
		tag._context = this._context
		tag.block()
	}

	fun append(tagName: String, kvs: Array<out TagAttrs>, block: TagBlock = {}) {
		append(TagX(tagName, *kvs), block)
	}
}