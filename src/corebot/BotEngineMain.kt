package com.turbomates.corebot

import com.turbomates.corebot.botauth.Authorization
import com.turbomates.corebot.botauth.BotAuth
import com.turbomates.corebot.botauth.MicrosoftAuthorise
import com.turbomates.corebot.botmessage.BotSenderData
import com.turbomates.corebot.botmessage.MessageSender
import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.botmessage.Sender
import com.turbomates.corebot.conversation.ConversationAdapter
import kotlinx.coroutines.channels.Channel

class BotEngineMain {

    private val messages = Channel<OutcomeMessage>()
    private val authorization = Channel<String>()

    fun build(): ConversationAdapter
    {
        return ConversationAdapter(messages)
    }

    suspend fun sendMessages() {

        val microsoftAuthorise = MicrosoftAuthorise(
            BotAuth("5", "CC")
        )

        Authorization.keepBotAuthorized(microsoftAuthorise, authorization)

    }

    suspend fun keepBotAuthorized()
    {
        val messageSender = MessageSender(
            BotSenderData(
                "5de0",
                "bot name",
                "http://localhost:58425"
            ),
            authorization
        )

        Sender.sendOutcomeMessages(messageSender, messages)
    }
}