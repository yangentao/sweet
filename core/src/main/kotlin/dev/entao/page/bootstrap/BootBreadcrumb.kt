package dev.entao.page.bootstrap

import dev.entao.page.tag.*

//<nav aria-label="breadcrumb">
//	<ol class="breadcrumb">
//		<li class="breadcrumb-item"><a href="#">Home</a></li>
//		<li class="breadcrumb-item"><a href="#">Library</a></li>
//		<li class="breadcrumb-item active" aria-current="page">Data</li>
//	</ol>
//</nav>

// block 返回 active
fun Tag.breadcrumb(itemCount: Int, itemCallback: (Tag, Int) -> Boolean) {
	this.nav("aria-label" to "breadcrumb") {
		ol("class" to "breadcrumb") {
			for (i in 0 until itemCount) {
				li("class" to "breadcrumb-item") {
					if (itemCallback(this, i)) {
						this classAdd "active"
					}
				}
			}
		}
	}
}