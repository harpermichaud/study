package io.github.harpertm.study.db.table

import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object UserPrivateTable : UuidTable("user_private") {
	val password = text("password")
}