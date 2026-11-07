package io.github.harpermichaud.study

import io.github.harpermichaud.study.db.Database
import io.github.harpermichaud.study.service.MigrationService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

// not needed for anything in an Application extension function, but needed elsewhere
val applicationLogger = LoggerFactory.getLogger(Application::class.java)

fun main(args: Array<String>) {
	val initialTime = measureTimeMillis {
		readConfig()
		Database.connect()

		// don't run in the case they're potentially executing migrations
		if (args.isEmpty()) {
			val isDbUpToDate = runBlocking { MigrationService.isDatabaseUpToDate() }
			if (!isDbUpToDate) {
				applicationLogger.error("There are pending migrations, run the executable again with the argument 'migration:execute'")
				exitProcess(1)
			}
		}
	}
	applicationLogger.info("Initial loading done in $initialTime ms")

	if (args.isNotEmpty()) when (args.first()) {
		"migration:executeAndStart" -> runBlocking { MigrationService.executeMigrations() }
		"migration:execute" -> runBlocking { MigrationService.executeMigrations(); exitProcess(0) }
		"migration:generate" -> runBlocking { MigrationService.generateMigrations(); exitProcess(0) }
	}

	embeddedServer(Netty, config.port, config.host, module = Application::module)
		.start(wait = true)
}

fun Application.module() {
	install(CallLogging)

	setupSerialization()
	setupAuthentication()

	setupRouting()
}