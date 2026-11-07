package io.github.harpermichaud.study.frontend.page

import io.github.harpermichaud.study.frontend.layout.LayoutDefault
import io.github.harpermichaud.study.model.FrontendContext
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