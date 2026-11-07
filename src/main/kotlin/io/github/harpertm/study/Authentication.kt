package io.github.harpertm.study

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.bearer

fun Application.setupAuthentication() {
	install(Authentication) {
		bearer {
			// todo: auth
		}
	}
}