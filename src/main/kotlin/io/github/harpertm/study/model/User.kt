package io.github.harpertm.study.model

import io.github.harpertm.study.db.table.UserTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.ResultRow

@Serializable
data class User(
	val id: String,
	val username: String,
	val admin: Boolean
) {
	companion object {
		fun mapFromDb(rr: ResultRow) = User(
			id = rr[UserTable.id].toString(),
			username = rr[UserTable.username],
			admin = rr[UserTable.admin]
		)
	}
}
