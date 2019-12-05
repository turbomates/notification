package com.turbomates.echobot

import com.google.inject.Inject
import com.turbomates.corebot.Bot
import com.turbomates.corebot.incomeactivity.Member
import com.turbomates.corebot.conversation.ConversationAdapter
import com.turbomates.corebot.incomeactivity.ConversationId

class EchoBot @Inject constructor(
    private val adapter: ConversationAdapter
): Bot
{
    override suspend fun onPersonsAdded(membersAdded: List<Member>, conversationId: ConversationId) {
        membersAdded.forEach { member ->
            adapter.greet("Пойдем-ка покурим-ка!", member, adapter.gatherConversation(conversationId))
        }
    }

    override suspend fun onBotAdded(conversationId: ConversationId) {
        adapter.write("Это бот присоединился!", adapter.gatherConversation(conversationId))
    }

    override suspend fun onMessage(incomeMessage: String, conversationId: ConversationId) {
        adapter.write("Ага, ${incomeMessage}, не до тебя щас!", adapter.gatherConversation(conversationId))
    }
}