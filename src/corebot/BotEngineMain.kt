package com.turbomates.corebot

import com.turbomates.corebot.botauth.Authorization
import com.turbomates.corebot.botauth.BotAuth
import com.turbomates.corebot.botauth.MicrosoftAuthorise
import com.turbomates.corebot.botmessage.BotSenderData
import com.turbomates.corebot.botmessage.MessageSender
import com.turbomates.corebot.botmessage.OutcomeMessage
import com.turbomates.corebot.botmessage.Sender
import com.turbomates.corebot.conversation.ConversationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BotEngineMain {

    private val messages = Channel<OutcomeMessage>()
    private val authorization = Channel<String>()
    private lateinit var config: BotConfig

    fun setup(id: String, pass: String, name: String, serverUrl: String): ConversationAdapter
    {
        config = BotConfig(BotAuth(id, pass), BotSenderData(id, name, serverUrl))
        return ConversationAdapter(messages)
    }

    suspend fun start() = withContext(Dispatchers.Default) {

        launch {
            val microsoftAuthorise = MicrosoftAuthorise(config.botAuth)
            Authorization.keepBotAuthorized(microsoftAuthorise, authorization)
        }

        launch {
            val messageSender = MessageSender(config.botSenderData, authorization)
            Sender.sendOutcomeMessages(messageSender, messages)

        }
    }
}

private data class BotConfig(val botAuth: BotAuth, val botSenderData: BotSenderData)