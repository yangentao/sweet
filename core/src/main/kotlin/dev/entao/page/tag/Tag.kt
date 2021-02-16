@file:Suppress("FunctionName", "MemberVisibilityCanBePrivate", "unused")

package dev.entao.page.tag

import dev.entao.base.userLabel
import dev.entao.core.ActionURL
import dev.entao.core.HttpAction
import dev.entao.core.HttpContext
import dev.entao.page.bootstrap.confirm
import java.util.*


//TODO 将tag的toString单独拿出来.
open class Tag(val httpContext: HttpContext, var tagName: String) {

	val children = ArrayList<Tag>(8)
	val attrs: AttrMap = AttrMap()
	var parent: Tag? = null
	var id: String by attrs
	var name: String by attrs
	var onclick: String by attrs

	constructor(parent: Tag, name: String) : this(parent.httpContext, name) {
		this.parent = parent
	}

	val root: Tag get() = this.parent?.root ?: this

	fun parent(block: (Tag) -> Boolean): Tag? {
		val p = this.parent ?: return null
		if (block(p)) {
			return p
		}
		return p.parent(block)
	}

	fun parent(attr: KeyValuePair, vararg vs: KeyValuePair): Tag? {
		val p = this.parent ?: return null
		if (p.match(attr, *vs)) {
			return p
		}
		return p.parent(attr, *vs)
	}

	fun first(block: (Tag) -> Boolean): Tag? {
		return children.find(block)
	}

	fun firstDeep(acceptor: (Tag) -> Boolean): Tag? {
		val t = children.find(acceptor)
		if (t != null) {
			return t
		}
		children.forEach {
			val tt = it.firstDeep(acceptor)
			if (tt != null) {
				return tt
			}
		}
		return null
	}


	fun list(attr: KeyValuePair, vararg vs: KeyValuePair): List<Tag> {
		return this.children.filter {
			it.match(attr, *vs)
		}
	}

	fun listDeep(attr: KeyValuePair, vararg vs: KeyValuePair): List<Tag> {
		val ls = ArrayList<Tag>()
		for (c in this.children) {
			if (c.match(attr, *vs)) {
				ls += c
			}
			ls += c.listDeep(attr, *vs)
		}
		return ls
	}

	fun first(attr: KeyValuePair, vararg vs: KeyValuePair): Tag? {
		for (c in this.children) {
			if (c.match(attr, *vs)) {
				return c
			}
		}
		return null
	}

	fun firstDeep(attr: KeyValuePair, vararg vs: KeyValuePair): Tag? {
		for (c in this.children) {
			if (c.match(attr, *vs)) {
				return c
			}
			val t = c.firstDeep(attr, *vs)
			if (t != null) {
				return t
			}
		}
		return null
	}

	fun single(tagname: String): Tag {
		return this.first(TAGNAME_ to tagname) ?: this.tag(tagname)
	}

	fun singleX(tagname: String, vararg vs: KeyValuePair): Tag {
		for (c in this.children) {
			if (c.tagName == tagname && c.match(*vs)) {
				return c
			}
		}
		return this.tag(tagname, *vs)
	}

	private fun match(vararg vs: KeyValuePair): Boolean {
		for (a in vs) {
			val c = when {
				a.first == TAGNAME_ -> this.tagName == a.second
				a.first == "class" -> this.hasClass(a.second)
				else -> this[a.first] == a.second
			}
			if (!c) {
				return false
			}
		}
		return true
	}


	fun hasClass(c: String): Boolean {
		val v = this["class"]
		if (v == c) {
			return true
		}
		return if (' ' in v) {
			v.startsWith("$c ") || v.endsWith(" $c") || v.contains(" $c ")
		} else {
			c == v
		}
	}


	fun idName(idname: String) {
		this["id"] = idname
		this["name"] = idname
	}

	fun needFor(controlTag: Tag?) {
		if (controlTag != null && this["for"].isEmpty()) {
			this["for"] = controlTag.needId()
		}
	}

	fun needId(): String {
		if (this["id"].isEmpty()) {
			this["id"] = generateElementId(tagName)
		}
		return this["id"]
	}

	fun addClassFirst(cls: String) {
		this["class"] = cls..this["class"]
	}


	fun bringToFirst() {
		val ls = parent?.children ?: return
		ls.remove(this)
		ls.add(0, this)
	}


	operator fun get(key: String): String {
		return attrs[key] ?: ""
	}


	operator fun set(key: String, value: String) {
		attrs[key] = value
	}


	operator fun get(attr: KeyValuePair, vararg vs: KeyValuePair): List<Tag> {
		return this.list(attr, *vs)
	}

	infix operator fun plusAssign(tag: Tag) {
		add(tag)
	}


	fun add(tag: Tag) {
		tag.parent = this
		this.children += tag
	}

	fun tag(tagname: String, vararg kv: KeyValuePair): Tag {
		val t = Tag(this, tagname)
		for (p in kv) {
			if (p.first == "class") {
				t += p.second
			} else {
				t[p.first] = p.second
			}
		}
		this += t
		return t
	}

	fun tag(tagname: String, vararg kv: KeyValuePair, block: TagCallback? = null): Tag {
		val t = this.tag(tagname, *kv)
		if (block != null) {
			t.block()
		}
		return t
	}


	//==textEscaped
	operator fun String?.unaryPlus() {
		textEscaped(this)
	}

	operator fun String?.not() {
		textUnsafe(this)
	}

	fun textUnsafe(block: () -> String?) {
		textUnsafe(block())
	}

	fun textUnsafe(text: String?) {
		if (text != null) {
			this += TextUnsafe(httpContext, text)
		}
	}

	fun textEscaped(block: () -> String?) {
		textEscaped(block())
	}

	fun textEscaped(text: String?): TextEscaped? {
		if (text != null) {
			val a = TextEscaped(httpContext, text)
			this += a
			return a
		}
		return null
	}

	companion object {
		private var eleId: Int = 1
		fun generateElementId(prefix: String = "element"): String {
			val aid = eleId++
			return "$prefix$aid"
		}
	}
}

fun Tag.addClass(clazz: String) {
	this["class"] = this["class"]..clazz
}


fun Tag.removeClass(clazz: String) {
	val s = this["class"]
	val ls = s.split(' ').map { it.trim() }.toMutableList()
	ls.remove(clazz)
	this["class"] = ls.joinToString(" ") { it.trim() }
}

fun Tag.setKeyValue(kv: KeyValuePair) {
	this[kv.first] = kv.second
}

fun Tag.setAttr(key: String, value: String) {
	this[key] = value
}

fun Tag.removeAttr(key: String) {
	this.attrs.remove(key)
}


infix operator fun Tag?.plusAssign(clazz: String) {
	this?.addClass(clazz)
}


infix operator fun Tag?.plusAssign(kv: KeyValuePair) {
	this?.setKeyValue(kv)
}


infix operator fun Tag?.minusAssign(clazz: String) {
	this?.removeClass(clazz)
}


fun Tag.setHttpAction(action: HttpAction) {
	val url = this.httpContext.filter.actionUri(action)
	this.setActionUrl(url)
	this.confirm(action)
	if (this.tagName == "button" || this.tagName == "a") {
		if (this.children.isEmpty()) {
			this.textEscaped(action.userLabel)
		}
	}
}

fun Tag.setActionURL(action: ActionURL) {
	val url = action.toURL(this.httpContext)
	this.setActionUrl(url)
	this.confirm(action.action)
	if (this.tagName == "button" || this.tagName == "a") {
		if (this.children.isEmpty()) {
			this.textEscaped(action.action.userLabel)
		}
	}
}

infix operator fun Tag?.plusAssign(action: HttpAction) {
	this?.setHttpAction(action)
}

infix operator fun Tag?.plusAssign(action: ActionURL) {
	this?.setActionURL(action)
}

fun Tag.setActionUrl(url: String) {
	if (this.tagName == "form") {
		this["action"] = url
	} else if (this.tagName == "a") {
		this["href"] = url
	} else if (this.tagName == "button") {
		this[DATA_URL_] = url
	} else if (this.tagName == "input" && this["type"] == "button") {
		this[DATA_URL_] = url
	} else {
		throw IllegalArgumentException("该tag不支持action属性")
	}
}
