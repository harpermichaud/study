package io.github.harpertm.study.route.api.form

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.harpertm.study.db.table.UserPrivateTable
import io.github.harpertm.study.db.table.UserTable
import io.github.harpertm.study.model.exception.ApiException
import io.github.harpertm.study.service.CacheService
import io.github.harpertm.study.util.BCRYPT_COST
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveParameters
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import java.util.UUID

fun Routing.register() {
	get("/register") {
		val formParameters = call.receiveParameters()

		val username = formParameters["username"]
			?: throw ApiException(HttpStatusCode.BadRequest, "Username required")
		val password = formParameters["password"]
			?: throw ApiException(HttpStatusCode.BadRequest, "Password required")

		if (username.isBlank())
			throw ApiException(HttpStatusCode.BadRequest, "Username must be at least 1 character")

		if (!"""^[a-zA-Z0-9._-]+$""".toRegex().matches(username))
			throw ApiException(HttpStatusCode.BadRequest, "Usernames must be alphanumeric, with the exception of ., _, and -")

		if (!CacheService.UsernameCache.isInitialized())
			throw ApiException(HttpStatusCode.ServiceUnavailable, "Caches still initializing")

		if (CacheService.UsernameCache.isTaken(username))
			throw ApiException(HttpStatusCode.BadRequest, "Username taken")

		if (password.isBlank() || password.length < 8)
			throw ApiException(HttpStatusCode.BadRequest, "Password must be at least 8 characters")

		// bcrypt constraint
		if (password.toByteArray().size > 72)
			throw ApiException(HttpStatusCode.BadRequest, "Password must be shorter, try 70 or less characters")

		val id = UUID.randomUUID()
		val hashedPassword = BCrypt.withDefaults().hashToString(BCRYPT_COST, password.toCharArray())

		throw ApiException(HttpStatusCode.NotImplemented, "Not implemented")

		suspendTransaction {
			UserTable.insert {
				it[UserTable.id] = id
				it[UserTable.username] = username
			}
			UserPrivateTable.insert {
				it[UserPrivateTable.id] = id
				it[UserPrivateTable.password] = hashedPassword
			}
		}

		//call.response.header("Set-Cookie", "StudyAuthToken=$token; Path=/; Max-Age=COOKIE_DURATION_SECONDS; SameSite=Lax")
		//call.respondRedirect("/")
	}
}