package com.turbomates.corebot.httpclient

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.post

class HttpClient {
    suspend inline fun <reified T> post(url: String, content: Any, postHeaders: List<Header>? = null): T {
        val client = client()
        val result = client.post<T>(url) {
            postHeaders?.forEach {
                headers.append(it.name, it.value)
            }
            body = content
        }
        client.close()
        return result
    }

    fun client(): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.BODY
            }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }
}

data class Header(val name: String, val value: String)