package io.github.harpertm.study.frontend.page

import io.github.harpertm.study.frontend.layout.LayoutDefault
import io.github.harpertm.study.model.FrontendContext
import kotlinx.html.HTML
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.passwordInput
import kotlinx.html.title

fun HTML.RegisterPage(
	ctx: FrontendContext
) {
	LayoutDefault(ctx, head = {
		title { +"Register" }
	}) {
		div(classes = "center-box") {
			div(classes = "content") {
				h1 { +"Register" }
				form {
					input {
						id = "username"
						placeholder = "Username"
					}
					passwordInput {
						id = "password"
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