@file:Suppress("unused", "FunctionName")

package dev.entao.core

import dev.entao.core.render.ResultRender

/**
 * Created by entaoyang@163.com on 2016/12/19.
 */

open class HttpGroup(final override val context: HttpContext) : HttpScope {

	val resultSender: ResultRender get() = context.resultSender
	val timeNow: Long = System.currentTimeMillis()

//	open fun indexAction() {
//		context.abort(404)
//	}
}
