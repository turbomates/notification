package com.turbomates.corebot.conversation

import com.turbomates.corebot.botmessage.ExternalId
import com.turbomates.corebot.botmessage.MessageId
import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.incomeactivity.ConversationId

class Conversation (val id: ConversationId){

    private val outcomeMessages = mutableListOf<OutcomeMessage>()

    fun push(message: OutcomeMessage) {
        outcomeMessages.add(message)
    }

    fun messageExternalLink(messageId: MessageId, externalId: ExternalId) {
        outcomeMessages.findLast { it.id == messageId }?.let { outcomeMessage: OutcomeMessage ->  outcomeMessage.linkWithExternalId(externalId) }
    }
}