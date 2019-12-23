package com.turbomates.corebot

import com.turbomates.corebot.botauth.Authorization
import com.turbomates.corebot.botauth.BotAuth
import com.turbomates.corebot.botauth.MicrosoftAuthorise
import com.turbomates.corebot.botmessage.*
import com.turbomates.corebot.conversation.ConversationAdapter
import com.turbomates.corebot.conversation.storage.InMemory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BotEngineMain {

    private val messages = Channel<OutcomeMessage>()
    private val authorization = Channel<String>()
    private val bindings = Channel<ExternalIdBinding>()
    private val storage = InMemory()
    private lateinit var config: BotConfig

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
            Binding.bindOutcomeMessages(storage, bindings)

        }
    }
}

private data class BotConfig(val botAuth: BotAuth, val botSenderData: BotSenderData)