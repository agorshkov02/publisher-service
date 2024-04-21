package com.github.agorshkov02

import java.io.File
import java.io.FileNotFoundException

data class ThemeDir(
    val description: String, val summary: String, val dir: String
)

class ThemeDirService(
    private val rootDirName: String
) {

    fun retrieveThemesGroups(): Iterable<ThemeDir> {
        val rootDir = File(rootDirName)

        if (!rootDir.exists() || !rootDir.canRead() || !rootDir.isDirectory()) {
            throw FileNotFoundException("$rootDir is not exists, not directory or not readable")
        }

        return rootDir.listFiles { file -> file.isDirectory }?.map {
            val description = File(it.path, DESCRIPTION_FILE_NAME)
            val summary = File(it.path, SUMMARY_FILE_NAME)
            when {
                !description.exists() || !description.canRead() -> throw FileNotFoundException("${it.path}/$DESCRIPTION_FILE_NAME is not exists or not readable")
                !summary.exists() || !summary.canRead() -> throw FileNotFoundException("${it.path}/$SUMMARY_FILE_NAME is not exists or not readable")
            }
            ThemeDir(
                description = description.readText(), summary = summary.readText(), dir = it.name
            )
        } ?: emptyList()
    }

    companion object {
        private const val DESCRIPTION_FILE_NAME = "_description.txt"
        private const val SUMMARY_FILE_NAME = "_summary.txt"
    }
}
