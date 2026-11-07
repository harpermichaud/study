package io.github.harpertm.study.db.table

import io.github.harpertm.study.util.MAX_USERNAME_LENGTH
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.CurrentDate
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.datetime.timestamp


object UserTable : UUIDTable("user") {
	val username = varchar("username", MAX_USERNAME_LENGTH).uniqueIndex()

	val createdAt = datetime("created_at")
		.defaultExpression(CurrentDateTime)
	val updatedAt = datetime("updated_at")
		.nullable()

	val admin = bool("admin").default(false)
}