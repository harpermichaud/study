package io.github.harpertm.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object CardTable : UuidTable("card") {
	val front = varchar("front", 100000)
	val back = varchar("back", 100000)
	val attachments = array<String>("attachments")
}