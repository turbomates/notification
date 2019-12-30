package com.turbomates.corebot.botmessage

import com.turbomates.corebot.incomeactivity.ConversationId
import java.util.*

class OutcomeMessage(val message: String, val conversationId: ConversationId) {
    val id = MessageId()
    private var externalId: ExternalId? = null

    fun linkWithExternalId(externalId: ExternalId) {
        this.externalId = externalId
    }
}

class MessageId {
    val id = UUID.randomUUID()
}

class ExternalId(val id: String)