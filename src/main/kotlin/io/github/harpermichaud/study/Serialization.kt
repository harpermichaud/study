package io.github.harpermichaud.study

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json

val json = Json {
	encodeDefaults = true
	prettyPrint = true
	isLenient = true
	explicitNulls = false
}

fun Application.setupSerialization() {
	install(ContentNegotiation) {
		json(json)
	}
}