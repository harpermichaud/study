package io.github.harpertm.study.service

import io.github.harpertm.study.db.table.CardTable
import io.github.harpertm.study.db.table.DeckTable
import io.github.harpertm.study.model.Card
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.select
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.r2dbc.update
import kotlin.uuid.Uuid

object DeckService {
	suspend fun getCards(deckId: Uuid): List<Card> =
		suspendTransaction {
			(CardTable leftJoin DeckTable)
				.selectAll()
				.where { CardTable.deck eq deckId }
				.map { Card.mapFromDb(it) }
				.toList()
		}

	suspend fun markView(deckId: Uuid, authenticated: Boolean = false) {
		val originalViews = suspendTransaction {
			DeckTable
				.select(
				if (authenticated) DeckTable.viewsAuthenticated
					else DeckTable.viewsUnauthenticated
				)
				.where { DeckTable.id eq deckId }
				.map {
					if (authenticated) it[DeckTable.viewsAuthenticated]
					else it[DeckTable.viewsUnauthenticated]
				}
				.singleOrNull()
		} ?: return

		suspendTransaction {
			DeckTable.update({ DeckTable.id eq deckId }) {
				if (authenticated) it[DeckTable.viewsAuthenticated] = originalViews + 1
				else it[DeckTable.viewsUnauthenticated] = originalViews + 1
			}
		}
	}
}