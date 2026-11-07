package io.github.harpertm.study.service

import io.github.harpertm.study.db.table.TokenTable
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import java.security.SecureRandom
import java.util.*

object UserService {
	suspend fun generateToken(userId: UUID): String {
		val values = ByteArray(64)
		SecureRandom().nextBytes(values)
		val generatedToken = values.contentToString()

		suspendTransaction {
			TokenTable.insert {
				it[user] = userId
				it[token] = generatedToken
			}
		}

		return generatedToken
	}
}