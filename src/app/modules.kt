package app

import app.services.StorageService
import app.services.StorageServiceImpl
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.ApplicationConfig
import org.koin.core.module.Module
import org.koin.dsl.module
import javax.sql.DataSource

fun initAppModule(config: ApplicationConfig): Module {
    return module(createdAtStart = true) {
        single { initDataSource(config) }
        single<StorageService> { StorageServiceImpl() }
    }
}

fun initDataSource(config: ApplicationConfig): DataSource {
    val dbHost = config.property("db.host").getString()
    val dbName = config.property("db.database").getString()
    val dbUser = config.property("db.username").getString()
    val dbPassword = config.property("db.password").getString()

    return HikariDataSource(HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://$dbHost/$dbName?sslmode=disable"
        username = dbUser
        password = dbPassword
    })
}