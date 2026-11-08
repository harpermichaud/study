package io.github.harpertm.study.frontend.page

import io.github.harpertm.study.frontend.layout.LayoutDefault
import io.github.harpertm.study.model.FrontendContext
import kotlinx.html.FormMethod
import kotlinx.html.HTML
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.input
import kotlinx.html.passwordInput
import kotlinx.html.title

fun HTML.LoginPage(
	ctx: FrontendContext
) {
	LayoutDefault(ctx, head = {
		title { +"Login" }
	}) {
		div(classes = "center-box") {
			div(classes = "content") {
				h1 { +"Login" }
				form(action = "/api/form/login", method = FormMethod.post) {
					input {
						name = "username"
						placeholder = "Username"
					}
					passwordInput {
						name = "password"
						placeholder = "Password"
					}

					div(classes = "submit-ctn") {
						button(classes = "btn") {
							+"Submit"
						}
					}
				}
			}
		}
	}
}