package com.github.agorshkov02

import io.ktor.server.config.*

fun ApplicationConfig.propertyAsString(path: String): String = property(path).getString()

fun Throwable.messageOrEmpty(): String = message ?: ""
