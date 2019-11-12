package com.turbomates.infrastructure

import com.google.inject.Inject
import com.google.inject.name.Named
import com.turbomates.model.bot.Activity
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ActivityHandler @Inject constructor(
    private val auth: AppAuthentication,
    @Named("botServerURL") private val serverURL: String
) {

    suspend fun handle(activity: Activity) {

        val client = HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.BODY
            }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
        client.post<String> {
            url("$serverURL/v3/conversations/${activity.conversation.id}/activities")
            contentType(ContentType.Application.Json)
            headers.append("Authorization","Bearer ${auth.bearer()}")
            body = activity
        }
        client.close()
    }
}
