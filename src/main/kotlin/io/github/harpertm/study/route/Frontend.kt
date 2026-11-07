package io.github.harpertm.study.route

import io.github.harpertm.study.frontend.page.IndexPage
import io.github.harpertm.study.frontend.page.RegisterPage
import io.github.harpertm.study.model.FrontendContext
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.get

fun RoutingCall.buildCtx(): FrontendContext =
	FrontendContext(this)

fun Routing.frontend() {
	get("/") { call.respondHtml { IndexPage(call.buildCtx()) } }
	get("/register") { call.respondHtml { RegisterPage(call.buildCtx()) } }
}