package io.github.harpertm.study.db.table

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp

object MigrationTable : IdTable<String>("migration") {
	override val id: Column<EntityID<String>> = varchar("id", 512)
		.entityId()

	val ranAt = timestamp("ran_at")
		.defaultExpression(CurrentTimestamp)

	override val primaryKey = PrimaryKey(id)
}