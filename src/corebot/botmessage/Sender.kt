package com.turbomates.corebot.botmessage

import com.turbomates.corebot.incomeactivity.Member
import com.turbomates.corebot.httpclient.Header
import com.turbomates.corebot.httpclient.HttpClient
import com.turbomates.corebot.middleware.ExternalIdLink
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

object Sender
{
    suspend fun sendOutcomeMessages(sender: MessageSender, channel: Channel<OutcomeMessage>)
    {
        while (true) {
            if (!channel.isEmpty) {
                sender.send(channel.receive())
            }
            delay(100)
        }
    }
}

class MessageSender(
    private val senderData: BotSenderData,
    private val authorization: Channel<String>,
    private val reverseLink: Channel<ExternalIdLink>
){

    private var currentAuthorization: String? = null

    suspend fun send(message: OutcomeMessage) {
        val client = HttpClient()

        if (!authorization.isEmpty) {
            currentAuthorization = authorization.receive()
        }
        if (currentAuthorization == null) {
            throw Exception("Could find authorization for send message")
        }

        val externalId = client.post<ExternalId>(
            "${senderData.serverUrl}/v3/conversations/${message.conversationId.id}/activities",
            Body("message", message.message, Member(senderData.id, senderData.name)),
            listOf(
                Header(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                Header(HttpHeaders.Authorization, currentAuthorization!!)
            )
        )

        reverseLink.send(
            ExternalIdLink(
                message.conversationId,
                message.id,
                externalId
            )
        )
    }
}

private data class Body(val type: String, val text: String, val from: Member)
data class BotSenderData(val id: String, val name: String, val serverUrl: String)
