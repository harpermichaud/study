package io.github.harpertm.study.model

import io.ktor.server.application.ApplicationCall

data class FrontendContext(
	val call: ApplicationCall
)
