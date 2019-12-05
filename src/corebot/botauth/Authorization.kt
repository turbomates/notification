package com.turbomates.corebot.botauth

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

object Authorization
{
    suspend fun keepBotAuthorized(authorise: MicrosoftAuthorise, authorization: Channel<String>)
    {
        while (true) {
            val token = authorise.get()
            authorization.send(token.value)
            delay(token.expiredInSec * 1000 - 100)
        }
    }
}

data class Token(val value: String, val expiredInSec: Long)