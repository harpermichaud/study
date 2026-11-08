package io.github.harpertm.study

import io.github.harpertm.study.db.Database
import io.github.harpertm.study.frontend.page.ErrorPage
import io.github.harpertm.study.model.exception.ApiException
import io.github.harpertm.study.route.buildCtx
import io.github.harpertm.study.service.CacheService
import io.github.harpertm.study.service.MigrationService
import io.github.harpertm.study.util.background
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.statuspages.StatusPages
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
			background { CacheService.initialize() }

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

	install(StatusPages) {
		exception<ApiException> { call, cause ->
			cause.printStackTrace()
			call.respondHtml(status = cause.status) { ErrorPage(call.buildCtx(), cause.message ?: "Internal server error") }
		}

		exception<Throwable> { call, cause ->
			cause.printStackTrace()
			call.respondHtml { ErrorPage(call.buildCtx(), cause.message ?: "Internal server error") }
		}
	}

	setupSerialization()

	setupRouting()
}