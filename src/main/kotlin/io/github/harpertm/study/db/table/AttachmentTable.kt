package io.github.harpertm.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object AttachmentTable : UuidTable("attachment") {
	val url = varchar("url", 100000)
	//val type = enumeration<>()
}