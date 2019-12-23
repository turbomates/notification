package com.turbomates.corebot.incomeactivity

/**
 * https://github.com/microsoft/botframework-sdk/tree/master/specs/botframework-activity
 */
data class IncomeActivity (
    val conversation: ConversationId,
    val type: String,
    val from: Member,
    val recipient: Member,
    val text: String?,
    val membersAdded: List<Member>?
)

data class Member (val id: String, val name: String)

data class ConversationId (val id: String)