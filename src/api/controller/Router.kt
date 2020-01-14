package com.turbomates.api.controller

import com.google.inject.*
import com.turbomates.corebot.incomeactivity.ConversationId
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.locations.Location
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.locations.*

class Router @Inject constructor(application: Application, injector: Injector) {
    init {

        application.routing {

            @Location("/api/notifications/{conversationId}")
            data class Notification(val conversationId: String)


            post("/api/messages") {
                injector.getInstance(IncomeActivityController::class.java).accept(call.receive())
                call.respondText("thanks", contentType = ContentType.Text.Plain)
            }


            authenticate("notification") {
                post<Notification> {
                    injector.getInstance(NotificationsController::class.java).receive(call.receive(), ConversationId(it.conversationId))
                    call.respondText("thanks", contentType = ContentType.Text.Plain)
                }
            }
        }
    }
}