package com.turbomates

import io.ktor.application.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*
import com.google.inject.*
import com.turbomates.api.controller.Router
import com.turbomates.corebot.conversation.ConversationAdapter
import com.natpryce.konfig.*
import com.turbomates.corebot.BotEngineMain
import com.turbomates.echobot.EchoBotModule
import kotlinx.coroutines.launch

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

    val config = EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("properties") overriding
            ConfigurationProperties.fromResource("properties_")

    val botEngine = BotEngineMain()
    //@todo setup returns conversation adapter
    val conversationAdapter = botEngine.setup(
        config[bot.microsoftAppId],
        config[bot.microsoftAppPassword],
        config[bot.name],
        config[bot.serverURL]
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

object bot: PropertyGroup() {
    val microsoftAppId by stringType
    val microsoftAppPassword by stringType
    val name by stringType
    val serverURL by stringType
}