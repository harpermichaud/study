package io.github.harpermichaud.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object DeckTable : UuidTable("deck") {
	val name = varchar("name", 256)
	val description = varchar("description", 2048)
	val creator = reference("creator", UserTable)
	val tags = array<String>("tags")

	// in case of spam for whatever reason, we can ignore unauthenticated views
	val viewsAuthenticated = integer("views_authenticated")
	val viewsUnauthenticated = integer("views_unauthenticated")
}