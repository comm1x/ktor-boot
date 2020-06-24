package functional

import common.testApp
import io.ktor.http.HttpMethod
import io.ktor.server.testing.handleRequest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PingTest {
    @Test
    fun `test ping`() = testApp {
        val response = handleRequest(HttpMethod.Get, "/ping").response
        assertEquals(200, response.status()?.value)
        assertEquals("pong", response.content)
    }

    @Test
    fun `test json`() = testApp {
        val response = handleRequest(HttpMethod.Get, "/json").response
        assertEquals(200, response.status()?.value)
        assertTrue(response.headers["content-type"]?.contains("application/json") ?: false)
    }

    @Test
    fun `test mocked service`() = testApp {
        val response = handleRequest(HttpMethod.Get, "/storage").response
        assertEquals(200, response.status()?.value)
        assertEquals("test files", response.content)
    }

    @Test
    fun `test 300 db connections`() {
        repeat(300) {
            testApp {
                val response = handleRequest(HttpMethod.Get, "/db").response
                assertEquals("John", response.content)
            }
        }
    }
}