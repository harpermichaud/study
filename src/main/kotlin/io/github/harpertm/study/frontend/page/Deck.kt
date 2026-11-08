package io.github.harpertm.study.frontend.page

import io.github.harpertm.study.frontend.layout.LayoutDefault
import io.github.harpertm.study.model.Deck
import io.github.harpertm.study.model.FrontendContext
import io.github.harpertm.study.util.user
import io.github.harpertm.study.util.userOrNull
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.i
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.title

fun HTML.DeckPage(
	ctx: FrontendContext,
	deck: Deck,
	didViewerStar: Boolean,
	stars: Long
) {
	LayoutDefault(ctx, head = {
		title { +deck.name }
	}) {
		div("deck") {
			div("header") {
				div("left") {
					h1 { +deck.name }
					span {
						+"by "
						a("/users/${deck.creator.username}") {
							+deck.creator.username
						}
					}
				}

				div("right") {
					if (ctx.call.userOrNull() != null) {
						if (ctx.call.user().id != deck.creator.id)
							a(classes = "btn danger", href = "/deck/${deck.id}/report") {
								i("ti ti-flag")
								+"Report"
							}

						if (ctx.call.user().id == deck.creator.id)
							form(action = "/api/form/decks/${deck.id}", method = FormMethod.delete) {
								button(classes = "btn danger", type = ButtonType.submit) {
									i("ti ti-trash")
									+"Delete"
								}
							}
					}
					
					form(action = "/api/form/decks/${deck.id}/star", method = FormMethod.post) {
						button(classes = "btn star${if (didViewerStar) " primary" else ""}", type = ButtonType.submit) {
							disabled = ctx.call.userOrNull() == null

							if (!didViewerStar) i("ti ti-star")
							else i("ti-filled ti-star-filled")

							+"Star"
							span("count") { +"$stars" }
						}
					}
				}
			}
			if (!deck.description.isNullOrBlank()) p { +deck.description }
			div("tags") {
				deck.tags.forEach {
					a(classes = "tag", href = "/tags/$it") { +it }
				}
			}
		}
	}
}