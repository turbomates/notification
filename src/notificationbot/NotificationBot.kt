package com.turbomates.notificationbot

import com.google.inject.Inject
import com.turbomates.corebot.Bot
import com.turbomates.corebot.incomeactivity.ConversationId
import com.turbomates.corebot.incomeactivity.Member

class NotificationBot @Inject constructor(
): Bot {
    override suspend fun onMessage(incomeMessage: String, conversationId: ConversationId) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun onPersonsAdded(membersAdded: List<Member>, conversationId: ConversationId) {
        //send user to integration
    }

    override suspend fun onBotAdded(conversationId: ConversationId) {
        //send all current users to integration
    }
}