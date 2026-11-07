package io.github.harpertm.study.service

import io.github.harpertm.study.db.table.*
import io.github.harpertm.study.model.exception.MigrationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.notInList
import org.jetbrains.exposed.v1.migration.r2dbc.MigrationUtils
import org.jetbrains.exposed.v1.r2dbc.insert
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.slf4j.LoggerFactory
import kotlin.io.path.Path
import kotlin.io.path.appendLines
import kotlin.time.Clock

@OptIn(ExperimentalDatabaseMigrationApi::class)
object MigrationService {
	private val logger = LoggerFactory.getLogger(MigrationService::class.java)

	val tables = arrayOf<Table>(
		MigrationTable,

		AttachmentTable,
		CardTable,
		DeckTable,
		StarTable,
		TokenTable,
		UserPrivateTable,
		UserTable,
	)

	suspend fun generateMigrations() {
		val addedMigrations = mutableListOf<String>()

		tables.forEach { table ->
			val name = "V${Clock.System.now().epochSeconds}_Migration_${table.tableName}"
			logger.info("Generating migration $name")

			suspendTransaction {
				MigrationUtils.generateMigrationScript(
					table,
					scriptDirectory = "src/io.github.harpertm.study.main/resources/migrations",
					scriptName = name,
				)
			}
			addedMigrations.add(name)
		}

		val manifestPath = Path("src/io.github.harpertm.study.main/resources/migrations/manifest.txt")
		// todo: check if migrations are blank before this
		manifestPath.appendLines(addedMigrations)
	}

	fun getManifest(): List<String> {
		val res = this::class.java.classLoader.getResource("migrations/manifest.txt")
			?: throw MigrationException("Migration manifest missing, binary likely corrupted")
		return res.readText().lines()
	}

	suspend fun isInitialized(): Boolean = suspendTransaction {
		connection().metadata {
			val tables = tableNamesByCurrentSchema(null)
			"public.migration" in tables.tableNames
		}
	}

	suspend fun isDatabaseUpToDate(): Boolean {
		if (!isInitialized()) return false

		val latest = suspendTransaction {
			MigrationTable.selectAll().orderBy(MigrationTable.id, SortOrder.DESC)
				.first()
		}

		val manifest = getManifest()

		return latest[MigrationTable.id].toString() != manifest.last()
	}

	suspend fun executeMigrations() {
		val manifest = getManifest()
		val pending = mutableListOf<String>()

		if (!isInitialized()) pending.addAll(manifest)
		else {
			val unexecuted = suspendTransaction {
				MigrationTable.selectAll()
					.where { MigrationTable.id notInList manifest }
					.orderBy(MigrationTable.id, SortOrder.DESC)
					.map { it[MigrationTable.id].toString() }
					.toList()
			}
			pending.addAll(unexecuted)
		}

		pending.forEach { migration ->
			if (migration.isBlank()) return@forEach

			logger.info("Executing ${migration}.sql")

			val raw = (this::class.java.classLoader.getResource("migrations/${migration}.sql")
				?: return@forEach).readText()

			logger.info("	$raw")
			suspendTransaction { exec(raw) }
			suspendTransaction {
				MigrationTable.insert {
					it[id] = migration
					it[ranAt] = Clock.System.now()
				}
			}
		}
	}
}