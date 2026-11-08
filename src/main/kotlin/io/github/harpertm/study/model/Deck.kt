package io.github.harpertm.study.model

import io.github.harpertm.study.db.table.DeckTable
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.ResultRow
import kotlin.uuid.Uuid

@Serializable
data class Deck(
	val id: Uuid,
	val name: String,
	val description: String?,
	val creator: User,
	val tags: List<String> = emptyList(),
	val public: Boolean,

	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime?,

	val viewsAuthenticated: Int,
	val viewsUnauthenticated: Int,
) {
	fun views() = viewsAuthenticated + viewsUnauthenticated

	companion object {
		fun mapFromDb(rr: ResultRow) = Deck(
			id = rr[DeckTable.id].value,
			name = rr[DeckTable.name],
			description = rr[DeckTable.description],
			creator = User.mapFromDb(rr),
			tags = rr[DeckTable.tags],
			public = rr[DeckTable.public],
			createdAt = rr[DeckTable.createdAt],
			updatedAt = rr[DeckTable.updatedAt],
			viewsAuthenticated = rr[DeckTable.viewsAuthenticated],
			viewsUnauthenticated = rr[DeckTable.viewsUnauthenticated],
		)
	}
}