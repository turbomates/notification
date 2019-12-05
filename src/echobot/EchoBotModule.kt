package com.turbomates.echobot

import com.google.inject.AbstractModule
import com.turbomates.corebot.Bot

class EchoBotModule(): AbstractModule() {
    override fun configure() {
        bind(Bot::class.java).to(EchoBot::class.java)
    }
}