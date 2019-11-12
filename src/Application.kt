package com.turbomates

import com.turbomates.api.controller.MessagesController
import com.turbomates.model.bot.Activity
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.logging.*
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.gson.*
import kotlinx.coroutines.async
import com.google.inject.*
import com.google.inject.name.Named
import com.google.inject.name.Names
import com.turbomates.api.Router
import io.ktor.util.AttributeKey

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging) {
        level = Level.INFO
       filter { call -> call.request.path().startsWith("/api") }
    }

    install(DataConversion)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    Guice.createInjector(MainModule(this))
}

val Application.botMicrosoftAppId get() =
    environment.config.property("bot.microsoftAppId").getString()

val Application.botMicrosoftAppPassword get() =
    environment.config.property("bot.microsoftAppPassword").getString()

val Application.botServerURL get() =
    environment.config.property("bot.serverURL").getString()


class MainModule(private val application: Application): AbstractModule() {
    override fun configure() {
        bind(Application::class.java).toInstance(application)
        bind(Router::class.java).asEagerSingleton()
        bindConstant().annotatedWith(Names.named("botMicrosoftAppId")).to(application.botMicrosoftAppId)
        bindConstant().annotatedWith(Names.named("botMicrosoftAppPassword")).to(application.botMicrosoftAppPassword)
        bindConstant().annotatedWith(Names.named("botServerURL")).to(application.botServerURL)
    }
}
