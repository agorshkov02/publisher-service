package com.github.agorshkov02.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowHeaders { true }
        anyHost()
    }
}
