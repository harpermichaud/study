package io.github.harpertm.study.frontend.layout

import io.github.harpertm.study.model.FrontendContext
import io.github.harpertm.study.util.user
import io.github.harpertm.study.util.userOrNull
import kotlinx.html.FlowContent
import kotlinx.html.HEAD
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.i
import kotlinx.html.input
import kotlinx.html.main
import kotlinx.html.styleLink

fun HTML.LayoutDefault(
	ctx: FrontendContext,

	head: HEAD.() -> Unit = {},
	block: FlowContent.() -> Unit
) {
	head {
		head()
		styleLink("/static/index.css")
		styleLink("/static/font.css")
		styleLink("/static/tabler-icons.min.css")
		styleLink("/static/tabler-icons-filled.min.css")
	}
	body {
		header {
			div(classes = "content") {
				div(classes = "left") {
					a(classes = "logo", href = "/") {
						i("ti ti-books")
					}

					if (ctx.call.userOrNull() != null) {
						a(classes = "nav", href = "/users/${ctx.call.user().username}/decks") {
							+"My Decks"
						}
					}
				}

				div(classes = "search") {
					input {
						this.attributes["aria-label"] = "Search"
						placeholder = "Search..."
					}
				}

				div(classes = "right") {
					if (ctx.call.userOrNull() != null) {
						a(classes="btn primary", href = "/decks/new") {
							i("ti ti-plus")
							+"New"
						}

						a(classes = "account-btn", href = "/users/${ctx.call.user().username}") {
							+ctx.call.user().username
						}
						a(classes = "logout-btn", href = "/logout") {
							i("ti ti-logout")
						}
					} else {
						a(classes = "btn primary", href = "/register") {
							+"Register"
						}
						a(classes = "btn", href = "/login") {
							+"Login"
						}
					}
				}
			}
		}
		main {
			block()
		}
		footer {}
	}
}