package dev.entao.page.bootstrap

import dev.entao.core.ActionURL
import dev.entao.page.tag.*

//<div class="card" style="width: 18rem;">
//	<img src="..." class="card-img-top" alt="...">
//	<div class="card-body">
//		<h5 class="card-title">Card title</h5>
//		<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
//		<a href="#" class="btn btn-primary">Go somewhere</a>
//	</div>
//</div>

fun Tag.cardHeaderActions(title: String, vararg acList: ActionURL) {
	cardHeader {
		div("class" to "d-flex") {
			span("class" to "h6".."mr-auto") { +title }
			span {
				for (ac in acList) {
					linkButtonX(ac, "class" to "btn-outline-info".."btn-sm".."mr-1")
				}
			}
		}
	}
}

fun Tag.cardBodyTitle(titleText: String, block: TagBlock) {
	this.card {
		cardHeaderH6 { +titleText }
		cardBody(block = block)
	}
}


fun Tag.card(vararg vs: TagAttr, block: TagBlock): Tag {
	return div("class" to "card", *vs, block = block)
}

fun Tag.cardHeader(vararg vs: TagAttr, block: TagBlock): Tag {
	return div("class" to "card-header", *vs, block = block)
}

fun Tag.cardHeaderH6(vararg vs: TagAttr, block: TagBlock): Tag {
	return h6("class" to "card-header", *vs, block = block)
}

fun Tag.cardBody(vararg vs: TagAttr, block: TagBlock): Tag {
	return div("class" to "card-body", *vs, block = block)
}

fun Tag.cardFooter(vararg vs: TagAttr, block: TagBlock): Tag {
	return div("class" to "card-footer", *vs, block = block)
}

fun Tag.cardTitle(vararg vs: TagAttr, block: TagBlock): Tag {
	return h5("class" to "card-title", *vs, block = block)
}

fun Tag.cardSubTitle(vararg vs: TagAttr, block: TagBlock): Tag {
	return h6("class" to "card-title".."text-muted".."mb-2", *vs, block = block)
}

fun Tag.cardText(vararg vs: TagAttr, block: TagBlock): Tag {
	return p("class" to "card-text", *vs, block = block)
}

fun Tag.cardImgTop(vararg vs: TagAttr, block: TagBlock): Tag {
	return img("class" to "card-img-top", *vs, block = block)
}

fun Tag.cardLink(vararg vs: TagAttr, block: TagBlock): Tag {
	return a("class" to "card-link", "href" to "#", *vs, block = block)
}