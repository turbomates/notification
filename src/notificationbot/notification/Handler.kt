package com.turbomates.notificationbot.notification

import com.google.inject.Inject
import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.conversation.ConversationAdapter
import com.turbomates.corebot.incomeactivity.ConversationId

class Handler @Inject constructor(
    private val conversationAdapter: ConversationAdapter
) {

    suspend fun receive(notification: Notification, conversationId: ConversationId) {
        conversationAdapter.write(OutcomeMessage("notification received: ${notification.message}", conversationId))
    }
}