package com.turbomates.corebot

import com.turbomates.corebot.incomeactivity.ConversationId
import com.turbomates.corebot.incomeactivity.Member

interface Bot {

    suspend fun onMessage(incomeMessage: String, conversationId: ConversationId)

    suspend fun onPersonsAdded(membersAdded: List<Member>, conversationId: ConversationId)

    suspend fun onBotAdded(conversationId: ConversationId)
}


