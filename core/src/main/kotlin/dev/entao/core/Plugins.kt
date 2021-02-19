@file:Suppress("unused")

package dev.entao.core

import dev.entao.base.MIN
import dev.entao.base.MyDate
import dev.entao.base.base64Encoded
import dev.entao.core.account.isAccountLogined
import dev.entao.log.Yog
import dev.entao.log.loge
import java.util.*
import kotlin.reflect.full.hasAnnotation


interface HttpTimer {
	//hour [0-23]
	fun onHour(h: Int) {}

	//m一直自增 , 会大于60
	fun onMinute(m: Int) {}

	//dayOfMonth [0-31]
	//hour [0-23]
	//minute [0-59]
	fun onHttpTimer(dayOfMonth: Int, hour: Int, minute: Int)
}

object MethodAcceptor : HttpSlice {
	override fun match(context: HttpContext, router: Router): Boolean {
		return router.methods.isNotEmpty()
	}

	override fun allowRouter(context: HttpContext, router: Router): Boolean {
		if (context.request.method.toUpperCase() !in router.methods) {
			context.abort(400, "Method Error")
			return false
		}
		return true
	}

}


class TimerSlice(callback: HttpTimer) : HttpSlice {


	private var filter: HttpFilter? = null
	private var timer: Timer? = null
	private val timerList = ArrayList<HttpTimer>()

	init {
		timerList += callback
	}

	fun addTimer(t: HttpTimer) {
		if (t !in this.timerList) {
			this.timerList += t
		}
	}

	override fun onInit(filter: HttpFilter) {
		this.filter = filter
		timer?.cancel()
		timer = null
		val tm = Timer("everyMinute", true)
		val delay: Long = 1.MIN
		tm.scheduleAtFixedRate(tmtask, delay, delay)
		timer = tm
	}

	override fun onDestory() {
		timer?.cancel()
		timer = null
		timerList.clear()
		filter = null
	}

	private val tmtask = object : TimerTask() {

		private var minN: Int = 0
		private var preHour = -1

		override fun run() {
			val date = MyDate()
			val day = date.day
			val h = date.hour
			val minute = date.minute
			val timers = ArrayList<HttpTimer>(timerList)
			timers.forEach {
				try {
					it.onHttpTimer(day, h, minute)
				} catch (ex: Exception) {
					ex.printStackTrace()
				}
			}

			if (h != preHour) {
				preHour = h
				for (ht in timers) {
					try {
						ht.onHour(h)
					} catch (ex: Exception) {
						loge(ex)
					}
				}
			}

			val n = minN++
			for (mt in timers) {
				try {
					mt.onMinute(n)
				} catch (ex: Exception) {
					loge(ex)
				}
			}

			try {
				Yog.flush()
			} catch (ex: Exception) {
			}
			filter?.cleanThreadLocals()
		}
	}

}



object LoginCheckSlice : HttpSlice {
	lateinit var filter: HttpFilter
	val loginUri: String by lazy {
		filter.findRouter { it.function.hasAnnotation<LoginAction>() }?.uri?.toLowerCase() ?: ""
	}

	override fun onInit(filter: HttpFilter) {
		super.onInit(filter)
		this.filter = filter
	}

	override fun match(context: HttpContext, router: Router): Boolean {
		return router.needLogin
	}

	override fun allowRouter(context: HttpContext, router: Router): Boolean {
		if (!context.isAccountLogined) {
			if (loginUri.isNotEmpty()) {
				if (context.acceptHtml) {
					var url = context.request.requestURI
					val qs = context.request.queryString ?: ""
					if (qs.isNotEmpty()) {
						url = "$url?$qs"
					}
					url = url.base64Encoded
					val u = Url(loginUri)
					u.replace(Keb.BACK_URL, url)
					context.redirect(u.build())
					return false
				}
			}
			context.abort(401)
			return false
		}
		return true
	}
}
