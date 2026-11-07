package io.github.harpertm.study.model.config

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
	val host: String = "0.0.0.0",
	val port: Int = 8080,

	val database: DatabaseConfiguration = DatabaseConfiguration()
)
