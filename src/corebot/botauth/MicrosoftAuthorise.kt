package com.turbomates.corebot.botauth

import com.turbomates.corebot.httpclient.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.Parameters

class MicrosoftAuthorise(private val auth: BotAuth) {
    suspend fun get(): Token
    {
        val token = HttpClient().post<MicrosoftToken>(
            "https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token",
            FormDataContent(
                Parameters.build {
                    append("grant_type", "client_credentials")
                    append("scope", "https://api.botframework.com/.default")
                    append("client_id", auth.id)
                    append("client_secret", auth.password)
                }
            )
        )

        return Token("${token.token_type} ${token.access_token}", token.expires_in)
    }
}

data class BotAuth(val id: String, val password: String)
private data class MicrosoftToken(val token_type: String, val expires_in: Long, val extExpiresIn: Long, val access_token: String)
