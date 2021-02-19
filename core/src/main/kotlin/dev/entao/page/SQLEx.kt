@file:Suppress("FunctionName", "unused")

package dev.entao.page

import dev.entao.base.*
import dev.entao.sql.*
import dev.entao.core.HttpContext
import dev.entao.core.HttpScope
import dev.entao.core.param
import dev.entao.core.paramNameSet
import javax.servlet.http.HttpServletRequest
import kotlin.reflect.full.hasAnnotation


fun <T : Model> ModelClass<T>.listPaged(context: HttpContext, w: Where?, sp: SortParam): List<T> {
	return this.list {
		where(w)
		orderBy(sp)
		limitPage(context)
	}
}

fun TableQuery.limitPage(context: HttpContext) {
	val n = context.params.int(P.pageArg) ?: 0
	this.limit(P.pageSize, n * P.pageSize)
}

fun TableQuery.orderBy(sp: SortParam) {
	if (sp.sortBy.isNotEmpty()) {
		if (sp.desc) {
			desc(sp.sortBy)
		} else {
			asc(sp.sortBy)
		}
	}
}

fun SQLQuery.limitPage(context: HttpContext) {
	val n = context.params.int(P.pageArg) ?: 0
	this.limit(P.pageSize, n * P.pageSize)
}

fun SQLQuery.orderBy(sp: SortParam) {
	if (sp.sortBy.isNotEmpty()) {
		if (sp.desc) {
			desc(sp.sortBy)
		} else {
			asc(sp.sortBy)
		}
	}
}

class SortParam(context: HttpContext, sortByName: String, desc: Boolean = true) {

	val sortBy: String
	val desc: Boolean

	init {
		val a = context.params.str(P.sortBy)
		val d = context.params.str(P.sortDesc) == "1"
		if (a != null) {
			this.sortBy = a
			this.desc = d
		} else {
			this.sortBy = sortByName
			this.desc = desc
		}
	}

	constructor(context: HttpContext, p: Prop1, desc: Boolean) : this(context, p.userName, desc)
}


private fun HttpScope.sqlParam(p: Prop1): Any? {
	if (p.isTypeInt) {
		return context.params.int(p)
	}
	if (p.isTypeLong) {
		return context.params.long(p)
	}
	if (p.isTypeFloat || p.isTypeDouble) {
		return context.params.double(p)
	}
	if (p.isTypeString) {
		val s = context.params.str(p) ?: return null
		if (s.isNotEmpty()) {
			return s
		}
	}
	return null
}

fun HttpScope.EQ(vararg ps: Prop1): Where? {
	var w: Where? = null
	for (p in ps) {
		val v = sqlParam(p) ?: continue
		w = w AND p.sqlFullName.EQ(v)
	}
	return w
}

// %value%
fun HttpScope.LIKE(p: Prop1): Where? {
	val v = httpParams.str(p)?.trim() ?: return null
	if (v.isEmpty()) {
		return null
	}
	return p LIKE """%$v%"""
}

// value%
fun HttpScope.LIKE_(p: Prop1): Where? {
	val v = httpParams.str(p)?.trim() ?: return null
	if (v.isEmpty()) {
		return null
	}
	return p LIKE """$v%"""
}

// %value
fun HttpScope._LIKE(p: Prop1): Where? {
	val v = httpParams.str(p)?.trim() ?: return null
	if (v.isEmpty()) {
		return null
	}
	return p LIKE """%$v"""
}

fun HttpScope.NE(p: Prop1): Where? {
	val v = sqlParam(p) ?: return null
	return p NE v
}

fun HttpScope.GE(p: Prop1): Where? {
	val v = sqlParam(p) ?: return null
	return p GE v
}

fun HttpScope.GT(p: Prop1): Where? {
	val v = sqlParam(p) ?: return null
	return p GT v
}

fun HttpScope.LE(p: Prop1): Where? {
	val v = sqlParam(p) ?: return null
	return p LE v
}

fun HttpScope.LT(p: Prop1): Where? {
	val v = sqlParam(p) ?: return null
	return p LT v
}

fun Model.fromRequest(context: HttpContext) {
	this.fromRequest(context.request)
}

fun Model.fromRequest(req: HttpServletRequest) {
	val nameSet = req.paramNameSet
	val thisInst = this
	this::class.modelProperties.forEach {
		val key = it.userName
		if (key in nameSet) {
			val sval = req.param(key)
			if (sval != null) {
				val v: Any? = if (it.hasAnnotation<Trim>()) {
					strToV(sval.trim(), it)
				} else {
					strToV(sval, it)
				}
				if (v != null || it.returnType.isMarkedNullable) {
					it.setValue(thisInst, v)
				} else {
					it.setValue(thisInst, defaultValueOfProperty(it))
				}
			}
		}
	}
}


