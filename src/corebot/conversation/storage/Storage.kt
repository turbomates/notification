package com.turbomates.corebot.conversation.storage

import com.turbomates.corebot.conversation.Conversation
import com.turbomates.corebot.incomeactivity.ConversationId

interface Storage {
    fun get(id: ConversationId): Conversation

    fun save(conversation: Conversation)
}