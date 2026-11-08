package io.github.harpertm.study.db.table

import io.github.harpertm.study.util.MAX_USERNAME_LENGTH
import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object UserTable : UuidTable("user") {
	val username = varchar("username", MAX_USERNAME_LENGTH).uniqueIndex()

	val createdAt = datetime("created_at")
		.defaultExpression(CurrentDateTime)
	val updatedAt = datetime("updated_at")
		.nullable()

	val admin = bool("admin").default(false)
}