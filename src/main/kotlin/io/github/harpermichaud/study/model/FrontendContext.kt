package io.github.harpermichaud.study.model

import io.ktor.server.routing.RoutingCall

data class FrontendContext(
	val call: RoutingCall,
	val user: User? = null
)
