package com.turbomates.infrastructure

import com.google.inject.Inject
import com.google.inject.name.Named
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Parameters

class AppAuthentication @Inject constructor(
    @Named("botMicrosoftAppId") private val microsoftAppId: String,
    @Named("botMicrosoftAppPassword") private val microsoftAppPassword: String
){
    suspend fun bearer(): String {

        val client = HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.BODY
            }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
        val token = client.submitForm<Token> (
            url = "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token",
            formParameters = Parameters.build {
                append("grant_type", "client_credentials")
                append("scope", "https://api.botframework.com/.default")
                append("client_id", microsoftAppId)
                append("client_secret", microsoftAppPassword)
            }
        )

        return token.access_token
    }
}

private data class Token(val token_type: String, val expires_in: Number, val extExpiresIn: Number, val access_token: String)