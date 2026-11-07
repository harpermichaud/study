package io.github.harpertm.study.frontend.page

import io.github.harpertm.study.frontend.layout.LayoutDefault
import io.github.harpertm.study.model.FrontendContext
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.h1
import kotlinx.html.title

fun HTML.ErrorPage(
	ctx: FrontendContext,
	message: String,
) {
	LayoutDefault(ctx, head = {
		title { +"Something went wrong" }
	}) {
		h1 { +message }
		a(classes = "btn", href = "/") { +"Go home" }
	}
}