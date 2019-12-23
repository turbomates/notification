package com.turbomates.corebot.conversation.storage

import com.turbomates.corebot.conversation.Conversation
import com.turbomates.corebot.incomeactivity.ConversationId

class InMemory: Storage {
    private val conversations = mutableMapOf<ConversationId, Conversation>()

    override fun get(id: ConversationId): Conversation {
        return conversations.getOrDefault(id, Conversation(id))
    }

    override fun save(conversation: Conversation) {
        conversations[conversation.id] = conversation
    }
}