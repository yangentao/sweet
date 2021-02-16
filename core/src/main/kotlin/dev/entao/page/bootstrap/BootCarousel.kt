package dev.entao.page.bootstrap

import dev.entao.page.tag.*


class CarouselItem {
	var imgSrc: String = ""
	var title: String = ""
	var desc: String = ""
	var linkTo: String = ""
}

fun Tag.buildCarousel(ident: String, items: List<CarouselItem>): Tag {
	return div("id" to ident, "class" to "carousel".."slide", "data-ride" to "carousel") {
		ol("class" to "carousel-indicators") {
			for (i in items.indices) {
				val t = li("data-target" to "#$ident", "data-slide-to" to "$i")
				if (i == 0) {
					t += "active"
				}
			}
		}
		div("class" to "carousel-inner") {
			for (i in items.indices) {
				val item = items[i]
				val t = div("class" to "carousel-item") {
					if (item.linkTo.isEmpty()) {
						img("class" to "d-block".."w-100", "src" to item.imgSrc)
					} else {
						a("href" to item.linkTo) {
							img("class" to "d-block".."w-100", "src" to item.imgSrc)
						}
					}
					div("class" to "carousel-caption".."d-none".."d-md-block") {
						h5 { +item.title }
						p { +item.desc }
					}
				}
				if (i == 0) {
					t += "active"
				}
			}
		}
		a("class" to "carousel-control-prev", "href" to "#$ident", "role" to "button", "data-slide" to "prev") {
			span("class" to "carousel-control-prev-icon", "aria-hidden" to "true")
			span("class" to "sr-only") { +"Previous" }
		}
		a("class" to "carousel-control-next", "href" to "#$ident", "role" to "button", "data-slide" to "next") {
			span("class" to "carousel-control-next-icon", "aria-hidden" to "true")
			span("class" to "sr-only") { +"Previous" }
		}
	}
}