package io.github.harpertm.study.model

import io.github.harpertm.study.db.table.UserTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.ResultRow
import kotlin.uuid.Uuid

@Serializable
data class User(
	val id: Uuid,
	val username: String,
	val admin: Boolean
) {
	companion object {
		fun mapFromDb(rr: ResultRow) = User(
			id = rr[UserTable.id].value,
			username = rr[UserTable.username],
			admin = rr[UserTable.admin]
		)
	}
}
