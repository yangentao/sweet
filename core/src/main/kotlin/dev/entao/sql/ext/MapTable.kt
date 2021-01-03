@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package dev.entao.sql.ext

import dev.entao.base.defaultValue
import dev.entao.base.userName
import dev.entao.base.defaultValueOfProperty
import dev.entao.base.strToV
import dev.entao.sql.*
import java.sql.Connection
import kotlin.reflect.KProperty

/**
 * Created by entaoyang@163.com on 2018/8/26.
 */

class MapTable(val tableName: String, val connName: String = "") {

	val mapCon: Connection
		get() = if (connName.isEmpty()) {
			ConnLook.defaultConnection
		} else {
			ConnLook.named(connName)
		}

	init {
		mayCreateTable(mapCon, tableName)
	}

	operator fun <V> setValue(thisRef: Any?, property: KProperty<*>, value: V) {
		if (value == null) {
			this.remove(property.userName)
		} else {
			this.put(property.userName, value.toString())
		}
	}

	@Suppress("UNCHECKED_CAST")
	operator fun <V> getValue(thisRef: Any?, property: KProperty<*>): V {
		val retType = property.returnType
		val v: String? = this.get(property.userName)
		if (v == null) {
			if (retType.isMarkedNullable) {
				return null as V
			}
		}
		if (v == null || v.isEmpty()) {
			val defVal = property.defaultValue
			if (defVal != null) {
				return strToV(defVal, property)
			}
			return defaultValueOfProperty(property)
		}
		return strToV(v, property)
	}

	fun putAll(map: Map<String, String>) {
		mapCon.trans { _ ->
			map.forEach {
				put(it.key, it.value)
			}
		}
	}

	fun put(key: String, value: String) {
		if (has(key)) {
			mapCon.update("UPDATE $tableName SET value_ = ?", listOf(value))
		} else {
			mapCon.update("INSERT INTO $tableName(key_,value_) VALUES(?,?)", listOf(key, value))
		}
	}

	fun remove(key: String): Int {
		return mapCon.update("DELETE FROM $tableName WHERE key_=?", listOf(key))
	}

	fun has(key: String): Boolean {
		return mapCon.query("SELECT 1 FROM $tableName WHERE key_ = ?", listOf(key)).existRow
	}

	fun get(key: String): String? {
		return mapCon.query("SELECT value_ FROM $tableName WHERE key_ = ?", listOf(key)).firstRow { it.getString(1) }
	}

	val mapValue: HashMap<String, String>
		get() {
			val m = HashMap<String, String>()
			val r = mapCon.query("SELECT key_, value_ FROM $tableName", emptyList())
			while (r.next()) {
				val k = r.getString(1)
				val v = r.getString(2) ?: ""
				m[k] = v
			}
			r.closeWithStatement()
			return m
		}

	companion object {
		private val nameSet = HashSet<String>()

		@Synchronized
		private fun mayCreateTable(mapCon: Connection, tableName: String) {
			if (tableName in nameSet) {
				return
			}
			nameSet.add(tableName)

			if (mapCon.tableExists(tableName)) {
				return
			}
			mapCon.createTable(tableName, listOf("key_ VARCHAR(256) PRIMARY KEY", "value_ VARCHAR(1024)"))
		}
	}

}