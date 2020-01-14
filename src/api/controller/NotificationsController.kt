package com.turbomates.api.controller

import com.google.inject.*
import com.turbomates.corebot.incomeactivity.ConversationId
import com.turbomates.notificationbot.notification.Notification
import com.turbomates.notificationbot.notification.Handler

class NotificationsController @Inject constructor(private val handler: Handler) {

    suspend fun receive(notification: Notification, conversationId: ConversationId) {
        handler.receive(notification, conversationId)
    }
}