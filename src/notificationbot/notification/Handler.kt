package com.turbomates.notificationbot.notification

import com.google.inject.Inject
import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.conversation.Conversation
import com.turbomates.corebot.incomeactivity.ConversationId

class Handler @Inject constructor(
    private val conversation: Conversation
) {

    suspend fun receive(notification: Notification, conversationId: ConversationId) {
        conversation.write(OutcomeMessage("notification received: ${notification.message}", conversationId))
    }
}