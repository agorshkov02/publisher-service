package com.github.agorshkov02.plugins

import com.github.agorshkov02.ColIsMissingException
import com.github.agorshkov02.ProductFile
import com.github.agorshkov02.ProductFileService
import com.github.agorshkov02.ThemeDir
import com.github.agorshkov02.ThemeDirService
import com.github.agorshkov02.messageOrEmpty
import com.github.agorshkov02.propertyAsString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.io.FileNotFoundException

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is ColIsMissingException -> {
                    call.respond(
                        HttpStatusCode.BadRequest, DetailResponse(cause.messageOrEmpty())
                    )
                }

                is FileNotFoundException -> {
                    call.respond(
                        HttpStatusCode.NotFound, DetailResponse(cause.messageOrEmpty())
                    )
                }

                else -> {
                    call.respond(
                        HttpStatusCode.InternalServerError, DetailResponse(cause.messageOrEmpty())
                    )
                }
            }
        }
    }

    val rootDirName = environment.config.propertyAsString("rootDirName")

    val productsFilesService = ProductFileService(rootDirName)
    val themesDirsService = ThemeDirService(rootDirName)

    routing {
        get("/ping") {
            call.respondText("pong")
        }

        get("/products/{themeDir}") {
            val themeDir = call.parameters["themeDir"]!!
            call.respond(
                productsFilesService.retrieveProductsFiles(themeDir).map(ProductFileResponse::from)
            )
        }

        get("/themes") {
            call.respond(themesDirsService.retrieveThemesGroups().map(ThemeDirResponse::from))
        }

        staticFiles("/$rootDirName", File(rootDirName))
    }
}

data class ProductFileResponse(
    val col: String, val content: String, val contentType: String
) {
    companion object {
        fun from(productFile: ProductFile) = ProductFileResponse(
            productFile.col.toString(), productFile.content, productFile.contentType.toString()
        )
    }
}

data class ThemeDirResponse(
    val summary: String, val description: String, val dir: String
) {
    companion object {
        fun from(themeDir: ThemeDir) = ThemeDirResponse(
            themeDir.summary, themeDir.description, themeDir.dir
        )
    }
}

data class DetailResponse(val detail: String)
