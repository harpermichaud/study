package io.github.harpertm.study.model.exception

import io.ktor.http.HttpStatusCode

class ApiException(val status: HttpStatusCode, message: String) : Exception(message)