package io.github.harpermichaud.study

import io.github.harpermichaud.study.model.config.Configuration
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.system.exitProcess

private var backingConfig = Configuration()
val config get() = backingConfig

fun writeConfig(path: Path = Path("./config.json")) {
	path.writeText(json.encodeToString(backingConfig))
}
fun readConfig(path: Path = Path("./config.json")) {
	if (!path.exists()) {
		writeConfig(path)
		applicationLogger.warn("Wrote default configuration, please configure and start again")
		exitProcess(1)
	}

	backingConfig = json.decodeFromString<Configuration>(path.readText())

	applicationLogger.info("Loaded configuration from $path")
}