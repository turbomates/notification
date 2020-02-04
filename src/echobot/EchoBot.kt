package com.turbomates.echobot

import com.google.inject.Inject
import com.turbomates.corebot.Bot
import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.incomeactivity.Member
import com.turbomates.corebot.conversation.Conversation
import com.turbomates.corebot.incomeactivity.ConversationId

class EchoBot @Inject constructor(
    private val adapter: Conversation
): Bot
{
    override suspend fun onPersonsAdded(membersAdded: List<Member>, conversationId: ConversationId) {
        membersAdded.forEach { member ->
            adapter.write(OutcomeMessage("Пойдем-ка покурим-ка!", conversationId))
        }
    }

    override suspend fun onBotAdded(conversationId: ConversationId) {
        adapter.write(OutcomeMessage("Это бот присоединился!", conversationId))
    }

    override suspend fun onMessage(incomeMessage: String, conversationId: ConversationId) {

        adapter.write(OutcomeMessage("Ага, ${incomeMessage}, не до тебя щас!", conversationId))
    }
}