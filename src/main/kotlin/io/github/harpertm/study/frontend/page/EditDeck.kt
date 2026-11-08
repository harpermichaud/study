package io.github.harpertm.study.frontend.page

import io.github.harpertm.study.frontend.layout.LayoutDefault
import io.github.harpertm.study.model.Deck
import io.github.harpertm.study.model.FrontendContext
import io.github.harpertm.study.util.MAX_DECK_DESCRIPTION_LENGTH
import io.github.harpertm.study.util.MAX_DECK_NAME_LENGTH
import io.github.harpertm.study.util.user
import io.github.harpertm.study.util.userOrNull
import kotlinx.html.ButtonType
import kotlinx.html.FormMethod
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.checkBoxInput
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.i
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.textArea
import kotlinx.html.title

fun HTML.EditDeckPage(
	ctx: FrontendContext,
	deck: Deck
) {
	LayoutDefault(ctx, head = {
		title { +"Editing ${deck.name}" }
	}) {
		div(classes = "center-box wide") {
			div(classes = "content") {
				h1 { +"Editing ${deck.name}" }
				form(action = "/api/form/deck/${deck.name}/edit", method = FormMethod.post) {
					input {
						name = "name"
						placeholder = "Name"
						maxLength = "$MAX_DECK_NAME_LENGTH"
					}
					textArea {
						name = "description"
						placeholder = "Describe your deck..."
						maxLength = "$MAX_DECK_DESCRIPTION_LENGTH"
						rows = "4"
					}
					input {
						name = "tags"
						placeholder = "Tags"
						maxLength = "$MAX_DECK_NAME_LENGTH"
					}
					span("input-desc") {
						+"Tags are helpful terms that will make your deck easier to search for. They could be the name of "
						+"a class, a subject, or relevant concepts. Separate with commas."
					}
					div(classes="checkbox-row") {
						checkBoxInput {
							this.id = "public"
							name = "public"
							checked = true
						}
						label {
							htmlFor = "public"
							+"Should your deck be public?"
						}
					}

					div(classes = "submit-ctn") {
						button(classes = "btn") {
							+"Create"
						}
					}
				}
			}
		}
	}
}