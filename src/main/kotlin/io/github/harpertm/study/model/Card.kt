package io.github.harpertm.study.model

import io.github.harpertm.study.db.table.CardTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.ResultRow
import kotlin.uuid.Uuid

@Serializable
data class Card(
	val id: Uuid,
	val front: String,
	val back: String,
	// deck is not in this, that is exclusively for querying
) {
	companion object {
		fun mapFromDb(rr: ResultRow) = Card(
			id = rr[CardTable.id].value,
			front = rr[CardTable.front],
			back = rr[CardTable.back]
		)
	}
}
