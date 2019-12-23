package com.turbomates.corebot.conversation

import com.turbomates.corebot.botmessage.ExternalId
import com.turbomates.corebot.botmessage.MessageId
import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.incomeactivity.ConversationId

class Conversation (val id: ConversationId){

    private val outcomeMessages = mutableMapOf<MessageId, OutcomeMessage>()

    fun push(message: OutcomeMessage) {
        outcomeMessages[message.id] = message
    }

    fun messageWasSent(messageId: MessageId, externalId: ExternalId) {
        outcomeMessages[messageId]?.let { outcomeMessage: OutcomeMessage ->  outcomeMessage.wasSent(externalId) }
    }
}