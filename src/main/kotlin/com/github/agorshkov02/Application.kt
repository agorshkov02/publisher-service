package com.github.agorshkov02

import com.github.agorshkov02.plugins.configureCORS
import com.github.agorshkov02.plugins.configureRouting
import com.github.agorshkov02.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureCORS()
    configureRouting()
    configureSerialization()
}
