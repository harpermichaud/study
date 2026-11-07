package io.github.harpertm.study

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
	}
}