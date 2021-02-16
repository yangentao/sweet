package dev.entao.page.tag

import dev.entao.base.isTypeBoolean
import dev.entao.base.isTypeInt
import dev.entao.base.isTypeString
import dev.entao.base.userName
import kotlin.reflect.KProperty

val TAGNAME_ = "tagname"

//用于button,
val DATA_URL_ = "data-url"
val DATA_SELECT_VALUE_ = "data-select-value"
val DATA_PARAM_NAME_ = "data-param-name"
val DATA_CONFIRM_ = "data-confirm"
val DATA_FORM_QUERY_ = "data-form-query"

typealias TagCallback = Tag.() -> Unit
typealias KeyValuePair = Pair<String, String>

infix operator fun String.rangeTo(s: String): String {
	if (this.isEmpty()) {
		return s
	}
	if (s.isEmpty()) {
		return this
	}
	return "$this $s"
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


