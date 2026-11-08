package io.github.harpertm.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object TokenTable : UuidTable("token") {
	val token = text("token")
	val user = reference("user", UserTable)
}