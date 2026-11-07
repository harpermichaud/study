package io.github.harpertm.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object StarTable : UuidTable("star") {
	val deck = reference("deck", DeckTable)
	val user = reference("user", UserTable)
}