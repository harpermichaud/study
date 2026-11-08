package io.github.harpertm.study.service

import io.github.harpertm.study.db.table.UserTable
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.neq
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.slf4j.LoggerFactory
import kotlin.system.measureTimeMillis
import kotlin.text.lowercase

object CacheService {
	private val logger = LoggerFactory.getLogger(CacheService::class.java)

	object UsernameCache {
		private var initialized = false
		fun isInitialized() = initialized

		private val cache = mutableSetOf<String>()
		fun getCache() = cache.toSet()

		fun add(username: String) { cache.add(username.lowercase()) }
		fun remove(username: String) { cache.remove(username.lowercase()) }
		fun isTaken(username: String) = username.lowercase() in cache

		suspend fun initialize() {
			val users = suspendTransaction {
				UserTable.selectAll()
					.where(UserTable.id neq null)
					.toList()
					.map { it[UserTable.username].lowercase() }
			}
			cache.addAll(users)
			initialized = true
		}
	}

	suspend fun initialize() {
		// im worried about this taking a long time in the future,
		// so this will stand out in logs if it ever were one
		val time = measureTimeMillis {
			UsernameCache.initialize()
		}

		logger.info("Initialized caches in $time ms")
	}
}