package com.turbomates.model.bot

data class Activity (
    val type: String,
    val conversation: Conversation,
    val recipient: Member?,
    val from: Member?,
    val text: String?,
    val membersAdded: List<Member>?
) {
    companion object {
        fun message(text: String, conversation: Conversation, from: Member): Activity {
            return Activity("message", conversation, null, from, text, null)
        }
    }
}
data class Member (val id: String, val name: String)
data class Conversation(val id: String)