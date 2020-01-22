package com.turbomates

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.turbomates.api.controller.Router
import com.turbomates.corebot.BotEngineMain
import com.turbomates.corebot.conversation.ConversationAdapter
import com.turbomates.echobot.EchoBotModule
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.basic
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DataConversion
import io.ktor.gson.gson
import io.ktor.locations.Locations
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false)  {


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


    val config = ConfigFactory.load()

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

    Guice.createInjector(MainModule(this, config), EchoBotModule())
}

class MainModule(private val application: Application, private val config: Config): AbstractModule() {

    override fun configure() {
        bind(Application::class.java).toInstance(application)
        bind(Router::class.java).asEagerSingleton()


        val conversationAdapter = BotEngineMain.setup(
            config.getString("bot.microsoftAppId"),
            config.getString("bot.microsoftAppPassword"),
            config.getString("bot.name"),
            config.getString("bot.serverURL")
        )
        bind(ConversationAdapter::class.java).toInstance(conversationAdapter)
    }
}