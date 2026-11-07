package io.github.harpertm.study.db.table

import io.github.harpertm.study.util.MAX_DECK_DESCRIPTION_LENGTH
import io.github.harpertm.study.util.MAX_DECK_NAME_LENGTH
import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object DeckTable : UuidTable("deck") {
	val name = varchar("name", MAX_DECK_NAME_LENGTH)
	val description = varchar("description", MAX_DECK_DESCRIPTION_LENGTH)
	val creator = reference("creator", UserTable)
	val tags = array<String>("tags")

	val createdAt = datetime("created_at")
		.defaultExpression(CurrentDateTime)
	val updatedAt = datetime("updated_at")
		.nullable()

	// in case of spam for whatever reason, we can ignore unauthenticated views
	val viewsAuthenticated = integer("views_authenticated")
	val viewsUnauthenticated = integer("views_unauthenticated")
}