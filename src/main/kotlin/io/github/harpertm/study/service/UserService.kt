package io.github.harpertm.study.service

import io.github.harpertm.study.db.table.TokenTable
import io.github.harpertm.study.db.table.UserTable
import io.github.harpertm.study.model.User
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import java.nio.charset.Charset
import java.security.SecureRandom
import kotlin.streams.asSequence
import kotlin.uuid.Uuid

object UserService {
	suspend fun generateToken(userId: Uuid): String {
		val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
		val generatedToken = SecureRandom()
			.ints(32, 0, chars.size)
			.asSequence()
			.map(chars::get)
			.joinToString("")

		suspendTransaction {
			TokenTable.insert {
				it[user] = userId
				it[token] = generatedToken
			}
		}

		return generatedToken
	}

	suspend fun getByToken(token: String): User? =
		suspendTransaction {
			(TokenTable leftJoin UserTable)
				.selectAll()
				.where { TokenTable.token eq token }
				.map { User.mapFromDb(it) }
				.singleOrNull()
		}
}