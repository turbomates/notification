package com.turbomates.corebot.botmessage

import com.turbomates.corebot.conversation.storage.Storage
import com.turbomates.corebot.incomeactivity.Member
import com.turbomates.corebot.httpclient.Header
import com.turbomates.corebot.httpclient.HttpClient
import com.turbomates.corebot.incomeactivity.ConversationId
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

object Binding
{
    suspend fun bindOutcomeMessages(storage: Storage, channel: Channel<ExternalIdBinding>)
    {
        while (true) {
            if (!channel.isEmpty) {
                val externalIdBinding = channel.receive()

                storage.get(externalIdBinding.conversationId).messageWasSent(
                    externalIdBinding.messageId,
                    externalIdBinding.externalId
                )
            }
            delay(100)
        }
    }
}

class MessageSender(
    private val senderData: BotSenderData,
    private val authorization: Channel<String>,
    private val binding: Channel<ExternalIdBinding>
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

        binding.send(
            ExternalIdBinding(
                message.conversationId,
                message.id,
                client.post<ExternalId>(
                    "${senderData.serverUrl}/v3/conversations/${message.conversationId.id}/activities",
                    Body("message", message.text, Member(senderData.id, senderData.name)),
                    listOf(
                        Header(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                        Header(HttpHeaders.Authorization, currentAuthorization!!)
                    )
                )
            )
        )
    }
}

private data class Body(val type: String, val text: String, val from: Member)
data class BotSenderData(val id: String, val name: String, val serverUrl: String)
data class ExternalIdBinding(val conversationId: ConversationId, val messageId: MessageId, val externalId: ExternalId)