package io.github.harpertm.study.route.api.form

import io.github.harpertm.study.db.table.DeckTable
import io.github.harpertm.study.model.exception.ApiException
import io.github.harpertm.study.util.authentication
import io.github.harpertm.study.util.user
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.post
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

@OptIn(ExperimentalUuidApi::class)
fun Routing.deck() {
	authentication(required = true) {
		post("/api/form/deck") {
			val formParameters = call.receiveParameters()

			val name = formParameters["name"]
				?: throw ApiException(HttpStatusCode.BadRequest, "Name required")
			val description = formParameters["description"]
			val tags = formParameters["tags"] ?: ""
			val public = formParameters["public"] ?: "on"

			val organizedTags = tags.split(",").map { it.trim() }

			val id = Uuid.generateV4()
			suspendTransaction {
				DeckTable.insert {
					it[DeckTable.id] = id
					it[DeckTable.name] = name
					it[DeckTable.description] = description
					it[DeckTable.creator] = call.user().id
					it[DeckTable.tags] = organizedTags
					it[DeckTable.public] = (public == "on")
				}
			}

			call.respondRedirect("/decks/$id")
		}

		post("/api/form/deck/edit") {
			throw ApiException(HttpStatusCode.NotImplemented, "Not implemented")
		}

		delete("/api/form/deck") {
			throw ApiException(HttpStatusCode.NotImplemented, "Not implemented")
		}

		post("/api/form/deck/star") {
			throw ApiException(HttpStatusCode.NotImplemented, "Not implemented")
		}
	}
}