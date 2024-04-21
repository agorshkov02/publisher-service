val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0-RC1"
    id("io.ktor.plugin")
}

group = "com.github.agorshkov02"
version = "0.0.1"

application {
    mainClass.set("com.github.agorshkov02.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.logback.classic)

    implementation(libs.ktor.serialization.jackson.jvm)

    implementation(libs.ktor.server.content.negotiation.jvm)

    implementation(libs.ktor.server.core.jvm)

    implementation(libs.ktor.server.host.common.jvm)

    implementation(libs.ktor.server.netty.jvm)

    implementation(libs.ktor.server.status.pages.jvm)

    implementation(Ktor.server.cors)

    testImplementation(libs.ktor.server.tests.jvm)

    testImplementation(Kotlin.test.junit)
}
