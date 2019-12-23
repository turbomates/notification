package com.turbomates.corebot.conversation

import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.conversation.storage.Storage
import com.turbomates.corebot.incomeactivity.ConversationId
import kotlinx.coroutines.channels.Channel

class ConversationAdapter (
    private val storage: Storage,
    private val channel: Channel<OutcomeMessage>
) {

    suspend fun write(message: OutcomeMessage) {
        channel.send(message)
        val conversation = gatherConversation(message.conversationId)
        conversation.push(message)
        storage.save(conversation)
    }

    fun gatherConversation(conversationId: ConversationId): Conversation
    {
        return storage.get(conversationId)
    }
}