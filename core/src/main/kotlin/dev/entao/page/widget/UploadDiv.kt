package dev.entao.page.widget

import dev.entao.base.getValue
import dev.entao.base.userName
import dev.entao.page.bootstrap.progress
import dev.entao.page.bootstrap.progressBar
import dev.entao.page.tag.*
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

fun Tag.configUpload(uploadUrl: String, viewUrl: String, viewUrlParam: String, uploadSizeLimitM: Int = 30, uploadDefaultFileImageUrl: String = "http://app800.cn/i/file.png") {
	script {
		val sb = StringBuilder(256)
		if (uploadUrl.isNotEmpty()) {
			sb.appendln("""Yet.uploadUrl = "$uploadUrl";""")
		}
		if (viewUrl.isNotEmpty()) {
			sb.appendln("""Yet.viewUrl = "$viewUrl";""")
		}
		if (viewUrlParam.isNotEmpty()) {
			sb.appendln("""Yet.viewUrlParam = "$viewUrlParam";""")
		}
		if (uploadSizeLimitM > 0) {
			sb.appendln("""Yet.uploadSizeLimitM = $uploadSizeLimitM;""")
		}
		if (uploadDefaultFileImageUrl.isNotEmpty()) {
			sb.appendln("""Yet.uploadDefaultFileImageUrl = "$uploadDefaultFileImageUrl";""")
		}
		sb.toString()
	}
}

fun Tag.uploadDiv(p: KProperty<*>) {
	if (p is KProperty0) {
		this.uploadDiv(p.userName, p.getValue()?.toString() ?: "0")
	} else {
		this.uploadDiv(p.userName, "0")
	}
}

fun Tag.uploadDiv(hiddenName: String, hiddenValue: String) {
	this.div(class_ to "form-control drag-upload-div", style_ to "height: 8em;") {
		hidden(hiddenName, hiddenValue)
		img(style_ to "height:5em;margin:4px;") {
		}
		progress(style_ to "height:0.2em") {
			progressBar {
			}
		}
		span(style_ to "font-size:50%") {
			+"将文件拖拽到此区域"
		}
		span(style_ to "font-size:50%") {
		}
		val myDivId = needId()
		script {
			"""
				$(function(){
					Yet.uploadBindDivById("$myDivId");
				});
				
			"""
		}
	}
}
