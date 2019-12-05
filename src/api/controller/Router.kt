package com.turbomates.api.controller

import com.google.inject.*
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.post
import io.ktor.routing.routing

class Router @Inject constructor(application: Application, injector: Injector) {
    init {

        application.routing {

            post("/api/messages") {
                injector.getInstance(IncomeActivityController::class.java).accept(call.receive())
                call.respondText("thanks", contentType = ContentType.Text.Plain)
            }
        }
    }
}