package common

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.createTestEnvironment
import io.ktor.server.testing.withApplication
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import javax.sql.DataSource

fun testApp(test: TestApplicationEngine.() -> Unit) {
    withApplication(createTestEnvironment {
        config = HoconApplicationConfig(ConfigFactory.load("application.conf"))
        startKoin { modules(initTestAppModule(config)) }
    }, {
    }, test)

    val koin = KoinContextHandler.get()
    val hikari = koin.get<DataSource>() as HikariDataSource
    hikari.close()
    stopKoin()
}