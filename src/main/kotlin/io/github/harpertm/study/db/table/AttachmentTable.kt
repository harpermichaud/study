package io.github.harpertm.study.db.table

import io.github.harpertm.study.util.MAX_CARD_TEXT_LENGTH
import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object AttachmentTable : UuidTable("attachment") {
	val url = varchar("url", MAX_CARD_TEXT_LENGTH)
	//val type = enumeration<>()
}