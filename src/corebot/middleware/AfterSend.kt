package com.turbomates.corebot.middleware

import com.turbomates.corebot.botmessage.ExternalId
import com.turbomates.corebot.botmessage.MessageId
import com.turbomates.corebot.incomeactivity.ConversationId
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

interface AfterSend {
    operator fun invoke(link: ExternalIdLink)
}

data class ExternalIdLink(val conversationId: ConversationId, val messageId: MessageId, val externalId: ExternalId)


suspend fun processAfterSend(channel: Channel<ExternalIdLink>, middleware: List<AfterSend>)
{
    while (true) {
        if (!channel.isEmpty) {
            val link = channel.receive()
            middleware.map { it(link) }

        }
        delay(100)
    }
}