package io.github.harpermichaud.study.model.config

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseConfiguration(
	val host: String = "127.0.0.1",
	val port: Int = 5432,
	val username: String = "study",
	val password: String = "",
	val database: String = "study"
)
