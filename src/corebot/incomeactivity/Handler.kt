package com.turbomates.corebot.incomeactivity

import com.turbomates.corebot.Bot

object Handler {

    suspend fun handle(activity: IncomeActivity, bot: Bot) {

        when (activity.type) {
            //@todo message could contain only attachments without text
            //Represents a communication between bot and user.
            "message" -> activity.text?.let { incomeMessage ->
                bot.onMessage(incomeMessage, activity.conversation)
            }
            //Indicates that the bot was added to a conversation, other members were added to or removed from the conversation, or conversation metadata has changed.
            "conversationUpdate" -> activity.membersAdded?.let { members ->

                members.filter { addedIsBot(it, activity.recipient) }
                    .let { bot.onBotAdded(activity.conversation)}

                members.filterNot { addedIsBot(it, activity.recipient)}
                    .let { persons -> bot.onPersonsAdded(persons, activity.conversation)}
            }
            //Indicates that the bot was added or removed from a user's contact list.
            "contactRelationUpdate" -> {}
            //Indicates that the user or bot on the other end of the conversation is compiling a response.
            "typing" -> {}
            //Indicates to a bot that a user has requested that the bot delete any user data it may have stored.
            "deleteUserData" -> {}
            //Indicates the end of a conversation.
            "endOfConversation" -> {}
            //Represents a communication sent to a bot that is not visible to the user.
            "event" -> {}
            //Represents a communication sent to a bot to request that it perform a specific operation. This activity type is reserved for internal use by the Microsoft Bot Framework.
            "invoke" -> {}
            //Indicates that a user has reacted to an existing activity. For example, a user clicks the "Like" button on a message.
            "messageReaction" -> {}
            else -> throw Exception("Don't know ${activity.type}")
        }
    }

    private fun addedIsBot(member: Member, bot: Member) = member.id == bot.id
}