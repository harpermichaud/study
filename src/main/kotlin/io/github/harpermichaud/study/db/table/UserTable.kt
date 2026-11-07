package io.github.harpermichaud.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object UserTable : UUIDTable("user") {
	val username = varchar("username", 128).uniqueIndex()
	val admin = bool("admin").default(false)
}