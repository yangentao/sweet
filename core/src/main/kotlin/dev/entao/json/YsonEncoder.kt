@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.json


import dev.entao.base.Exclude
import dev.entao.base.isPublic
import dev.entao.base.userName
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties

class YsonEncoderConfig {
	val map: HashMap<KClass<out Any>, YsonConverter> = HashMap(16)

	operator fun get(cls: KClass<*>): YsonConverter? {
		return map[cls]
	}
}

object YsonEncoder {

	fun encode(m: Any?, config: YsonEncoderConfig?): YsonValue {
		if (m == null) {
			return YsonNull.inst
		}
		if (m is YsonValue) {
			return m
		}
		val toy = config?.get(m::class) ?: YsonCoders[m::class]
		if (toy != null) {
			return toy.toYsonValue(m)
		}
		return when (m) {
			is Array<*> -> {
				val ya = YsonArray()
				m.mapTo(ya.data) { encode(it, config) }
				ya
			}

			is Iterable<*> -> {
				val ya = YsonArray()
				m.mapTo(ya.data) { encode(it, config) }
				ya
			}
			is Map<*, *> -> {
				val yo = YsonObject(m.size)
				for ((k, v) in m) {
					yo.data[k.toString()] = encode(v, config)
				}
				yo
			}
			else -> {
				val ls = m::class.memberProperties.filter { it.isPublic && it is KMutableProperty1 && !it.isAbstract && !it.isConst && !it.hasAnnotation<Exclude>() }
				val yo = YsonObject(ls.size)
				ls.forEach { p ->
					val k = p.userName
					val v = p.getter.call(m)
					yo.data[k] = encode(v, config)
				}
				yo
			}
		}
	}
}