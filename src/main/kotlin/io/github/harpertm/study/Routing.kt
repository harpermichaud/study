package io.github.harpertm.study

import io.github.harpertm.study.route.api.form.deck
import io.github.harpertm.study.route.api.form.login
import io.github.harpertm.study.route.api.form.logout
import io.github.harpertm.study.route.api.form.register
import io.github.harpertm.study.route.frontend
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.setupRouting() {
	routing {
		staticResources("/static", "static")

		get("/api/v1/health") {
			call.respondText("ok")
		}

		frontend()

		register()
		login()
		logout()
		deck()
	}
}