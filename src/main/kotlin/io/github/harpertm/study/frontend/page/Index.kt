package io.github.harpertm.study.frontend.page

import io.github.harpertm.study.frontend.layout.LayoutDefault
import io.github.harpertm.study.model.FrontendContext
import kotlinx.html.HTML
import kotlinx.html.h1
import kotlinx.html.title

fun HTML.IndexPage(
	ctx: FrontendContext
) {
	LayoutDefault(ctx, head = {
		title { +"Home" }
	}) {
		if (ctx.user != null) {
			h1 { +"Recently Used" }
			h1 { +"Pinned" }
		}
		h1 { +"Popular" }
	}
}