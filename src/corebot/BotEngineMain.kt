package com.turbomates.corebot

import com.turbomates.corebot.botauth.Authorization
import com.turbomates.corebot.botauth.BotAuth
import com.turbomates.corebot.botauth.MicrosoftAuthorise
import com.turbomates.corebot.botmessage.*
import com.turbomates.corebot.conversation.ConversationAdapter
import com.turbomates.corebot.conversation.storage.InMemory
import com.turbomates.corebot.middleware.ExternalIdLink
import com.turbomates.corebot.middleware.Log
import com.turbomates.corebot.middleware.ReverseLinking
import com.turbomates.corebot.middleware.processAfterSend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mu.KotlinLogging

class BotEngineMain {

    private val messages = Channel<OutcomeMessage>()
    private val authorization = Channel<String>()
    private val bindings = Channel<ExternalIdLink>()
    private val storage = InMemory()
    private lateinit var config: BotConfig
    private val logger = KotlinLogging.logger {}

    fun setup(id: String, pass: String, name: String, serverUrl: String): ConversationAdapter
    {
        config = BotConfig(BotAuth(id, pass), BotSenderData(id, name, serverUrl))
        return ConversationAdapter(storage, messages)
    }

    suspend fun start() = withContext(Dispatchers.Default) {

        launch {
            val microsoftAuthorise = MicrosoftAuthorise(config.botAuth)
            Authorization.keepBotAuthorized(microsoftAuthorise, authorization)
        }

        launch {
            val messageSender = MessageSender(config.botSenderData, authorization, bindings)
            Sender.sendOutcomeMessages(messageSender, messages)

        }

        launch {
            processAfterSend(bindings, listOf(Log(logger), ReverseLinking(storage)))
        }
    }
}

private data class BotConfig(val botAuth: BotAuth, val botSenderData: BotSenderData)