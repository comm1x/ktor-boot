import app.initAppModule
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    val appEnvironment = commandLineEnvironment(args)
    startKoin { modules(initAppModule(appEnvironment.config)) }
    embeddedServer(Netty, appEnvironment).start(true)
}