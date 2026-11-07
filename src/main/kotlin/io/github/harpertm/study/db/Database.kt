package io.github.harpertm.study.db

import io.github.harpertm.study.config
import org.jetbrains.exposed.v1.r2dbc.R2dbcDatabase
import org.slf4j.LoggerFactory

object Database {
	private val logger = LoggerFactory.getLogger(Database::class.java)

	lateinit var connection: R2dbcDatabase

	fun connect() {
		if (::connection.isInitialized)
			return logger.warn("Attempted second initialization of database")

		connection = R2dbcDatabase.connect(
			url = "r2dbc:postgresql://${config.database.host}:${config.database.port}/${config.database.database}",
			driver = "postgresql",
			user = config.database.username,
			password = config.database.password
		)
		logger.info("Connected to database")
	}
}