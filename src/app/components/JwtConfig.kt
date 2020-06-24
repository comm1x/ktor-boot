package app.components

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.Principal
import java.util.*
import java.util.concurrent.TimeUnit

object JwtConfig {
    private const val secret = "my-secret" // Change this line
    private val validityInMs = TimeUnit.HOURS.toMillis(2)
    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun makeToken(userId: Int): String = JWT.create()
        .withClaim("id", userId)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}

data class UserPrincipal(val id: Int): Principal