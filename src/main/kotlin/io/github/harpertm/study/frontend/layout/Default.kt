package io.github.harpertm.study.frontend.layout

import io.github.harpertm.study.model.FrontendContext
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
	}
	body {
		header {
			div(classes = "content") {
				div(classes = "left") {
					a(classes = "logo", href = "/") {
						i("ti ti-books")
					}

					if (ctx.user != null) {
						a(classes = "nav", href = "/sets/mine") {
							+"My Sets"
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
					if (ctx.user != null) {
						a(classes = "account-btn", href = "/account") {
							+ctx.user.username
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