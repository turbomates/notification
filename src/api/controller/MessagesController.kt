package com.turbomates.api.controller

import com.turbomates.model.TurnContext
import com.turbomates.model.bot.Activity
import com.turbomates.model.bot.EchoBot
import com.google.inject.*
import com.turbomates.infrastructure.ActivityHandler

class MessagesController @Inject constructor(
    private val bot: EchoBot,
    private val activityHandler: ActivityHandler
) {

    suspend fun messages(activity: Activity) {
        val turnContext = TurnContext(activity)
        when(activity.type) {
            "message" -> bot.onMessage(turnContext)
            "conversationUpdate" -> bot.onMembersAdded(turnContext)
            else -> throw Exception("Don't know ${activity.type}")
        }
        //@todo move to events
        if (null !== turnContext.last()) {
            activityHandler.handle(turnContext.last()!!)
        }
    }
}