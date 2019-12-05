package com.turbomates.notificationbot

import com.google.inject.AbstractModule
import com.turbomates.corebot.Bot

class NotificationBotModule: AbstractModule() {
    override fun configure() {
        bind(Bot::class.java).to(NotificationBot::class.java)
    }
}