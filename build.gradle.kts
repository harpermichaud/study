plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(ktorLibs.plugins.ktor)
	alias(libs.plugins.kotlin.serialization)
}

group = "io.github.harpermichaud"
version = "1.0.0-SNAPSHOT"

application {
	mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
	jvmToolchain(21)
}
dependencies {
	implementation(ktorLibs.serialization.kotlinx.json)
	implementation(ktorLibs.server.auth)
	implementation(ktorLibs.server.autoHeadResponse)
	implementation(ktorLibs.server.callId)
	implementation(ktorLibs.server.callLogging)
	implementation(ktorLibs.server.config.yaml)
	implementation(ktorLibs.server.contentNegotiation)
	implementation(ktorLibs.server.core)
	implementation(ktorLibs.server.forwardedHeader)
	implementation(ktorLibs.server.netty)
	implementation(ktorLibs.server.openapi)
	implementation(ktorLibs.server.requestValidation)
	implementation(ktorLibs.server.routingOpenapi)
	implementation(ktorLibs.server.swagger)
	implementation(ktorLibs.server.thymeleaf)
	implementation(libs.exposed.core)
	implementation(libs.exposed.r2dbc)
	implementation(libs.flaxoos.ktor.server.rateLimiting)
	implementation(libs.logback.classic)
	implementation(libs.postgresql)
	implementation("org.postgresql:r2dbc-postgresql:1.1.1.RELEASE")
	implementation("org.jetbrains.exposed:exposed-migration-core:1.6.1")
	implementation("org.jetbrains.exposed:exposed-migration-r2dbc:1.6.1")
	implementation("org.jetbrains.exposed:exposed-kotlin-datetime:1.6.1")
	implementation(ktorLibs.server.htmlBuilder)
}
