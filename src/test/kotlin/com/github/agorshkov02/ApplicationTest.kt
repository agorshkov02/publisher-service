package com.github.agorshkov02

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testPing() = testApplication {
        val response = client.get("/ping")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("pong", response.bodyAsText())
    }
}
