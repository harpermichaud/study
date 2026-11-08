package io.github.harpertm.study.service

import io.github.harpertm.study.db.table.CardTable
import io.github.harpertm.study.db.table.DeckTable
import io.github.harpertm.study.model.Card
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
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
}