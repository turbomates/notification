package com.turbomates.corebot.conversation

import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.botmessage.Text
import com.turbomates.corebot.incomeactivity.ConversationId
import com.turbomates.corebot.incomeactivity.Member
import kotlinx.coroutines.channels.Channel

class ConversationAdapter (
    private val channel: Channel<OutcomeMessage>
) {

    suspend fun write(message: String, conversation: Conversation) {
        //@todo save conversation state
        channel.send(Text(message, conversation.id))
    }

    suspend fun greet(message: String, member: Member, conversation: Conversation) {
        //@todo save conversation state
        channel.send(Text(message, conversation.id))
    }

    fun gatherConversation(conversationId: ConversationId): Conversation
    {//@todo build conversation with state here
        return Conversation(conversationId)
    }
}