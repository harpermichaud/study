package io.github.harpermichaud.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object TokenTable : UUIDTable("token") {
	val token = text("token")
	val user = reference("user", UserTable)
}