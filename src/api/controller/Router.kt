package com.turbomates.api

import com.google.inject.*
import com.turbomates.api.controller.MessagesController
import com.turbomates.model.bot.Activity
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.post
import io.ktor.routing.routing

class Router @Inject constructor(application: Application, injector: Injector) {
    init {

        application.routing {

            post("/api/messages") {

                injector.getInstance(MessagesController::class.java).messages(call.receive<Activity>())

                call.respondText("thanks", contentType = ContentType.Text.Plain)
//
//                client.post<Unit> {
//                    url("http://localhost:51030/")
//                    body = "BYE BYE!"
//                }
            }
        }

//        application.routing {
//            get("/") {
//                call.respondHtml {
//                    head {
//                        title { +"Ktor: guice" }
//                    }
//                    body {
//                        p {
//                            +message
//                        }
//                        p {
//                            +"Call Information: ${call.injector.getInstance(CallService::class.java).information()}"
//                        }
//                    }
//                }
//            }
//        }
    }
}