package io.github.harpermichaud.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object UserPrivateTable : UUIDTable("user_private") {
	val password = text("password")
}