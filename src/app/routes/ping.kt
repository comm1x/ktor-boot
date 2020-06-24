package app.routes

import app.services.StorageService
import com.vladsch.kotlin.jdbc.session
import com.vladsch.kotlin.jdbc.sqlQuery
import com.vladsch.kotlin.jdbc.using
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import org.koin.ktor.ext.inject
import java.time.ZonedDateTime
import javax.sql.DataSource

fun Route.pingRoutes() {
    val ds: DataSource by inject()
    val storage: StorageService by inject()

    get("/ping") {
        call.respond("pong")
    }

    get("/json") {
        call.respond(mapOf(
            "ints" to listOf(5, 9, 14),
            "now" to ZonedDateTime.now()
        ))
    }

    get("/db") {
        val names = using(session(ds)) {
            it.list(sqlQuery("SELECT * FROM users WHERE id=? LIMIT 1", 1)
            ) { row -> row.string("name") }
        }

        call.respond(names.first())
    }

    get("/storage") {
        call.respond(storage.getFiles())
    }
}