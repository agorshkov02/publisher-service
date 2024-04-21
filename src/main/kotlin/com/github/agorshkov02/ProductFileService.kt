package com.github.agorshkov02

import java.io.File
import java.io.FileNotFoundException

class ColIsMissingException(name: String) : Exception("$name doesn't contains col-*")

data class ProductFile(
    val col: Col, val content: String, val contentType: ContentType
)

enum class Col(private val className: String) {
    COL_6("col-6"), COL_12("col-12");

    override fun toString(): String = className
}

enum class ContentType {
    IFRAME, IMAGE_ALL;

    override fun toString(): String = name.lowercase()
}

class ProductFileService(
    private val rootDirName: String
) {

    private enum class AllowedExtension {
        HTML, GIF, JPG, PNG;

        override fun toString(): String = name.lowercase()
    }

    fun retrieveProductsFiles(themeDirName: String): Iterable<ProductFile> {
        val themeDir = File(rootDirName, themeDirName)

        if (!themeDir.exists() || !themeDir.isDirectory()) {
            throw FileNotFoundException("$themeDirName is not exists, not directory or not readable")
        }

        return themeDir.listFiles { file -> ALLOWED_EXTENSIONS.contains(file.extension()) }?.map {
            val name = it.name
            val col = when {
                name.contains(Col.COL_6.toString()) -> Col.COL_6
                name.contains(Col.COL_12.toString()) -> Col.COL_12
                else -> throw ColIsMissingException(name)
            }
            if (ALLOWED_EXTENSIONS[it.extension()] == AllowedExtension.HTML) {
                ProductFile(col = col, content = it.readText(), contentType = ContentType.IFRAME)
            } else {
                ProductFile(col = col, content = it.path, contentType = ContentType.IMAGE_ALL)
            }
        } ?: emptyList()
    }

    companion object {
        private val ALLOWED_EXTENSIONS = AllowedExtension.entries.associateBy { it.toString() }
    }
}

private fun File.extension() = name.split(".").last()
