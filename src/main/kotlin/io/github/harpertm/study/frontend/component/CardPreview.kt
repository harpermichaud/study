package io.github.harpertm.study.frontend.component

import io.github.harpertm.study.model.Card
import io.github.harpertm.study.model.User
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.span

fun FlowContent.CardPreviewComponent(
	card: Card,
	user: User?
) {
	div("card-preview") {
		div("top") {
			span { +card.front }
		}
		div("bottom") {
			span { +card.back }
		}
	}
}