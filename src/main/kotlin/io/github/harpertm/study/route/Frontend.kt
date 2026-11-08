package io.github.harpertm.study.route

import io.github.harpertm.study.db.table.DeckTable
import io.github.harpertm.study.db.table.UserTable
import io.github.harpertm.study.frontend.page.DeckPage
import io.github.harpertm.study.frontend.page.IndexPage
import io.github.harpertm.study.frontend.page.LoginPage
import io.github.harpertm.study.frontend.page.NewDeckPage
import io.github.harpertm.study.frontend.page.RegisterPage
import io.github.harpertm.study.model.Deck
import io.github.harpertm.study.model.FrontendContext
import io.github.harpertm.study.model.exception.ApiException
import io.github.harpertm.study.service.DeckService
import io.github.harpertm.study.util.authentication
import io.github.harpertm.study.util.user
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.get
import io.ktor.server.routing.toRoutingContext
import io.ktor.server.util.getOrFail
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.singleOrNull
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.mapLazy
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import kotlin.uuid.Uuid

fun ApplicationCall.buildCtx(): FrontendContext =
	FrontendContext(this)

fun Routing.frontend() {
	authentication {
		get("/") { call.respondHtml { IndexPage(call.buildCtx()) } }
		get("/register") { call.respondHtml { RegisterPage(call.buildCtx()) } }
		get("/login") { call.respondHtml { LoginPage(call.buildCtx()) } }

		get("/decks/{id}") {
			val id = call.parameters.getOrFail<Uuid>("id")

			val deck = suspendTransaction {
				(DeckTable leftJoin UserTable)
					.selectAll()
					.where { DeckTable.id eq id }
					.map { Deck.mapFromDb(it) }
					.singleOrNull()
			} ?: throw ApiException(HttpStatusCode.NotFound, "Deck not found")

			if (!deck.public && deck.creator.id != call.user().id)
				throw ApiException(HttpStatusCode.NotFound, "Deck not found")

			val cards = DeckService.getCards(deck.id)

			call.respondHtml { DeckPage(call.buildCtx(), deck, cards, false, 0) }
		}
	}

	authentication(required = true) {
		get("/decks/new") { call.respondHtml { NewDeckPage(call.buildCtx()) } }
	}
}