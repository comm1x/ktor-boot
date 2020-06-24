package app

import app.components.JwtConfig
import app.components.UserPrincipal
import app.routes.pingRoutes
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing

fun Application.apiModule() {
    install(ContentNegotiation) {
        jackson {
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            registerModule(JavaTimeModule())
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
            configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false)
        }
    }

    install(Compression) {
        default()
    }

    install(Authentication) {
        jwt {
            realm = "my-ktor-boot-realm"
            verifier(JwtConfig.verifier)
            validate {
                val id = it.payload.getClaim("id").asInt()
                return@validate UserPrincipal(id)
            }
        }
    }

    install(CORS) {
        anyHost()
    }

    routing {
        pingRoutes()
    }
}