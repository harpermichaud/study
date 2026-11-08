package io.github.harpertm.study.util

import io.github.harpertm.study.model.User
import io.github.harpertm.study.model.exception.ApiException
import io.github.harpertm.study.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.auth.AuthenticationRouteSelector
import io.ktor.server.routing.Route
import io.ktor.util.AttributeKey

val authenticatedUserKey = AttributeKey<User>("authenticatedUser")

fun ApplicationCall.user() = attributes[authenticatedUserKey]
fun ApplicationCall.userOrNull() = attributes.getOrNull(authenticatedUserKey)

fun Route.authentication(
	required: Boolean = false,
	route: Route.() -> Unit
): Route {
	val authRoute = createChild(AuthenticationRouteSelector(listOf("custom")))

	if (!required) authRoute.install(AuthOptional)
	else authRoute.install(AuthRequired)
	authRoute.route()

	return authRoute
}

private suspend fun internalAuth(call: ApplicationCall, required: Boolean) {
	val authCookie = call.request.cookies["StudyAuthToken"]

	val user: User? = if (authCookie != null) UserService.getByToken(authCookie) else null
	if (required && user == null) throw ApiException(HttpStatusCode.Unauthorized, "Authentication required")
	if (user != null) call.attributes[authenticatedUserKey] = user
}

private val AuthRequired = createRouteScopedPlugin("authRequired") {
	onCall { internalAuth(it, true) }
}

private val AuthOptional = createRouteScopedPlugin("authOptional") {
	onCall { internalAuth(it, false) }
}