package com.turbomates.corebot.incomeactivity

import com.turbomates.corebot.Bot

object Handler {

    suspend fun handle(activity: IncomeActivity, bot: Bot) {

        when (activity.type) {
            //@todo message could contain only attachments without text
            "message" -> activity.text?.let { incomeMessage ->
                bot.onMessage(incomeMessage, activity.conversation)
            }
            "conversationUpdate" -> activity.membersAdded?.let { members ->

                members.filter { addedIsBot(it, activity.recipient) }
                    .let { bot.onBotAdded(activity.conversation)}

                members.filterNot { addedIsBot(it, activity.recipient)}
                    .let { persons -> bot.onPersonsAdded(persons, activity.conversation)}
            }
            else -> throw Exception("Don't know ${activity.type}")
        }
    }

    private fun addedIsBot(member: Member, bot: Member) = member.id == bot.id
}