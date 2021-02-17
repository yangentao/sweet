package dev.entao.page.tag

import dev.entao.base.*
import java.util.regex.Pattern
import kotlin.reflect.KProperty

val TAGNAME_ = "tagname"

//用于button,
val DATA_URL_ = "data-url"
val DATA_SELECT_VALUE_ = "data-select-value"
val DATA_PARAM_NAME_ = "data-param-name"
val DATA_CONFIRM_ = "data-confirm"
val DATA_FORM_QUERY_ = "data-form-query"

typealias TagBlock = Tag.() -> Unit
typealias TagAttr = Pair<String, String>

infix operator fun String.rangeTo(s: String): String {
	if (this.isEmpty()) {
		return s
	}
	if (s.isEmpty()) {
		return this
	}
	return TagClass(this).add(s).value
}

fun main() {
	println(TagClass("1 a bc").remove("a").value)
	println(TagClass("1 a bc").bringFirst("a").value)
	println(TagClass("1 a bc").addFirst("ddd").value)
}

class TagClass(var value: String) {
	fun add(cls: String): TagClass {
		if (!has(cls)) {
			value = "$value $cls"
		}
		return this
	}

	fun remove(cls: String): TagClass {
		val n = value.indexOf(cls)
		when {
			n < 0 -> {
			}
			n + cls.length == value.length -> value = ""
			n == 0 && value[n + cls.length] == ' ' -> {
				value = value.substring(n + cls.length + 1)
			}
			n == value.length - cls.length && value[value.length - cls.length] == ' ' -> {
				value.substr(0, value.length - cls.length - 1)
			}
			value[n - 1] == ' ' && value[n + cls.length] == ' ' -> {
				value = value.substring(0, n) + value.substring(n + cls.length + 1)
			}
			else -> {
			}
		}
		return this
	}

	fun has(cls: String): Boolean {
		val n = value.indexOf(cls)
		return when {
			n < 0 -> false
			n + cls.length == value.length -> true
			n == 0 -> value[n + cls.length] == ' '
			n == value.length - cls.length -> value[value.length - cls.length] == ' '
			else -> value[n - 1] == ' ' && value[n + cls.length] == ' '
		}


	}

	fun bringFirst(cls: String): TagClass {
		return addFirst(cls)
	}

	fun addFirst(cls: String): TagClass {
		remove(cls)
		value = "$cls $value"
		return this
	}

	companion object {

	}
}

@Suppress("UNCHECKED_CAST")
class AttrMap : LinkedHashMap<String, String>() {


	operator fun <T> setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		val k = property.userName
		when (value) {
			is String -> put(k, value)
			is Boolean -> {
				if (value) {
					put(k, k)
				} else {
					remove(k)
				}
			}
			else -> put(k, value.toString())
		}
	}

	operator fun <T> getValue(thisRef: Any?, property: KProperty<*>): T {
		val pname = property.userName
		val rt = property.returnType
		val v = this[pname] ?: ""
		if (rt.isTypeString) {
			return v as T
		}
		if (rt.isTypeBoolean) {
			return (v == pname) as T
		}
		if (rt.isTypeInt) {
			if (v.isEmpty()) {
				return 0 as T
			} else {
				return v.toInt() as T
			}
		}
		throw IllegalArgumentException("不支持的类型$property")
	}

}


