package dev.entao.core

import dev.entao.base.*
import dev.entao.json.YsonArray
import dev.entao.json.YsonObject
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

/**
 * Created by entaoyang@163.com on 2018/4/2.
 */

class ParamError(val function: KFunction<*>, val param: KParameter, val msg: String) : RuntimeException("$function : $param $msg")

//function 必须是类的成员, 不能是全局函数
class Router(val uri: String, val cls: KClass<*>, val function: KFunction<*>, obj: Any? = null) {

	//	val cls: KClass<*> = function.ownerClass!!
	private val inst: Any? = obj ?: function.ownerObject
	private val paramList = function.parameters

	val needToken: Boolean = cls.hasAnnotation<NeedToken>() || function.hasAnnotation<NeedToken>()
	val needLogin: Boolean = cls.hasAnnotation<NeedLogin>() || function.hasAnnotation<NeedLogin>()

	val methods: Set<String> by lazy {
		val a = HashSet<String>()
		val b = function.findAnnotation<HttpMethod>()?.value?.map { it.toUpperCase() }?.toSet()
		if (b != null) {
			a.addAll(b)
		}
		val c = cls.findAnnotation<HttpMethod>()?.value?.map { it.toUpperCase() }?.toSet()
		if (c != null) {
			a.addAll(c)
		}
		a
	}

	fun dispatch(context: HttpContext) {
		val a = inst ?: cls.primaryConstructor?.call(context)
		?: throw IllegalAccessError("没有恰当的构造函数:" + cls.qualifiedName)

		try {
			val map = prepareParams(context, a)
			function.callBy(map)
		} catch (ex: Throwable) {
			onErr(context, ex)
		} finally {

		}
	}

	private fun onErr(context: HttpContext, ex: Throwable) {
		when (ex) {
			is ParamError -> {
				ex.printStackTrace()
				if (context.acceptJson) {
					context.resultSender.failed("参数错误:${ex.message}")
				} else {
					context.backError("错误: ${ex.msg}${ex.param.name}")
				}
				return
			}
			is InvocationTargetException -> onErr(context, ex.targetException)
			else -> {
				val c = ex.cause
				if (c != null) {
					onErr(context, c)
				} else {
					throw ex
				}
			}
		}
	}

	private fun prepareParams(context: HttpContext, inst: Any): HashMap<KParameter, Any?> {
		val map = HashMap<KParameter, Any?>()
		for (p in paramList) {
			if (p.kind == KParameter.Kind.EXTENSION_RECEIVER) {
				map[p] = inst
			} else if (p.kind == KParameter.Kind.INSTANCE) {
				map[p] = inst
			} else if (p.type.isClass(HttpContext::class)) {
				map[p] = context
			} else {
				val v = context.params.str(p)
				if (v == null || v.isEmpty()) {
					if (!p.isOptional) {
						if (p.type.isMarkedNullable) {
							map[p] = null
						} else {
							err(p, "缺少参数")
						}
					}
				} else {
					map[p] = valueOf(p, v)
				}
			}
		}
		return map
	}

	private fun err(p: KParameter, msg: String) {
		throw ParamError(function, p, msg)
	}

	private fun valueOf(p: KParameter, paramValue: String): Any {
		var value: String = paramValue
		if (p.hasAnnotation<Trim>() || p.hasAnnotation<NotBlank>()) {
			value = value.trim()
		}
		for (an in p.annotations) {
			when (an) {
				is NotEmpty -> if (value.isEmpty()) {
					err(p, "参数不能为空")
				}
				is NotBlank -> if (value.trim().isEmpty()) {
					err(p, "参数不能为空或不可见字符")
				}
				is Length -> if (value.length > an.value) {
					err(p, "参数长度须小于${an.value}")
				}
				is LengthRange -> if (value.length > an.maxValue) {
					err(p, "参数内容太长")
				} else if (value.length < an.minValue) {
					err(p, "参数 内容太短")
				}
				is Match -> if (!value.matches(an.value.toRegex())) {
					err(p, if (an.msg.isEmpty()) "参数规则不匹配" else an.msg)
				}
				is MinValue -> if (value.toDouble() < an.value.toDouble()) {
					err(p, if (an.msg.isEmpty()) "参数太小" else an.msg)
				}
				is MaxValue -> if (value.toDouble() > an.value.toDouble()) {
					err(p, if (an.msg.isEmpty()) "参数太大" else an.msg)
				}
				is ValueRange -> {
					val n = value.toDouble()
					if (n < an.minVal.toDouble()) {
						err(p, if (an.msg.isEmpty()) "参数太小" else an.msg)
					}
					if (n > an.maxVal.toDouble()) {
						err(p, if (an.msg.isEmpty()) "参数太大" else an.msg)
					}
				}
			}
		}
		return when (p.type.classifier) {
			String::class -> value
			Double::class -> value.toDouble()
			Float::class -> value.toFloat()
			Long::class -> value.toLong()
			Int::class -> value.toInt()
			Short::class -> value.toShort()
			Byte::class -> value.toByte()
			Boolean::class -> {
				if (value == "on" || value == "1" || value == "true") {
					true
				} else {
					value.toBoolean()
				}
			}
			YsonObject::class -> YsonObject(value)
			YsonArray::class -> YsonArray(value)
			else -> err(p, "不支持的参数类型")
		}
	}
}

