package dev.entao.core

import dev.entao.base.firstParamName
import dev.entao.base.urlEncoded
import dev.entao.base.userName
import kotlin.math.min
import kotlin.reflect.KParameter
import kotlin.reflect.full.valueParameters

class ActionURL(val action: HttpAction) {
	val map = LinkedHashMap<String, String>()
	private var currentIndex: Int = 0
	private val params: List<KParameter> by lazy {
		action.valueParameters
	}

	fun toURL(context: HttpContext): String {
		val s = context.filter.actionUri(action)
		if (map.isEmpty()) {
			return s
		}
		val b = map.map { it.key + "=" + it.value.urlEncoded }.joinToString("&")
		return "$s?$b"
	}

	fun fullURL(context: HttpContext): String {
		return context.fullUrlOf(this.toURL(context))
	}

	fun bindValueAt(index: Int, v: Any?): ActionURL {
		val argName = action.valueParameters[index].userName
		map[argName] = v?.toString() ?: ""
		return this
	}


	infix fun bindValue(v: Any?): ActionURL {
		return bindValueAt(currentIndex++, v)
	}

	fun bindValues(vararg vs: Any): ActionURL {
		for (v in vs) {
			bindValue(v)
		}
		return this
	}

	fun bindKeyValue(key: String, value: Any?): ActionURL {
		map[key] = value?.toString() ?: ""
		return this
	}

	infix fun bindKeyValue(p: Pair<String, Any>): ActionURL {
		return bindKeyValue(p.first, p.second)
	}

	fun bindKeyValues(vararg ps: Pair<String, Any>): ActionURL {
		for (p in ps) {
			bindKeyValue(p.first, p.second)
		}
		return this
	}
}


fun testAction(a: Int, b: String) {
	println(a)
	println(b)
}

fun main() {
	val a = ::testAction.action..123..456 bindKeyValue ("name" to "Yang")
//	a bind 123 bind "entao"
//	a.bindKeyValue("name" to "Yang")

	println(a.map)
}


operator fun ActionURL.rangeTo(v: Any): ActionURL {
	return this.bindValue(v)
}

operator fun ActionURL.rangeTo(v: Pair<String, Any>): ActionURL {
	return this.bindKeyValue(v)
}

val HttpAction.action: ActionURL get() = ActionURL(this)

operator fun HttpAction.unaryPlus(): ActionURL {
	return ActionURL(this)
}

operator fun HttpAction.rangeTo(v: Any): ActionURL {
	return ActionURL(this).bindValue(v)
}



