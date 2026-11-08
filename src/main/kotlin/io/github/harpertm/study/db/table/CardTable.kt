package io.github.harpertm.study.db.table

import io.github.harpertm.study.util.MAX_CARD_TEXT_LENGTH
import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object CardTable : UuidTable("card") {
	val front = varchar("front", MAX_CARD_TEXT_LENGTH)
	val back = varchar("back", MAX_CARD_TEXT_LENGTH)
	val deck = reference("deck", DeckTable)
	val attachments = array<String>("attachments")
}