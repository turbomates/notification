package com.turbomates

import io.ktor.application.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*
import com.google.inject.*
import com.turbomates.api.controller.Router
import com.turbomates.corebot.conversation.ConversationAdapter
import com.turbomates.corebot.BotEngineMain
import com.turbomates.echobot.EchoBotModule
import com.typesafe.config.ConfigFactory
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.locations.Locations
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false)  {

    val config = ConfigFactory.load()

    install(CallLogging) {
        level = Level.INFO
    }
    install(DataConversion)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Locations)
    install(Authentication) {
        basic(name = "notification") {
            validate { credentials ->
                if (credentials.name == config.getString("notification.name") && credentials.password == config.getString("notification.password")){
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }

    val botEngine = BotEngineMain()
    //@todo setup returns conversation adapter
    val conversationAdapter = botEngine.setup(
        config.getString("bot.microsoftAppId"),
        config.getString("bot.microsoftAppPassword"),
        config.getString("bot.name"),
        config.getString("bot.serverURL")
    )

    Guice.createInjector(MainModule(this, conversationAdapter), EchoBotModule())
    launch {
        botEngine.start()
    }
}


class MainModule(private val application: Application, private val conversationAdapter: ConversationAdapter): AbstractModule() {

    override fun configure() {
        bind(Application::class.java).toInstance(application)
        bind(Router::class.java).asEagerSingleton()
        bind(ConversationAdapter::class.java).toInstance(conversationAdapter)
    }
}