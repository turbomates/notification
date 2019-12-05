package com.turbomates.corebot.botmessage

import com.turbomates.corebot.incomeactivity.ConversationId

data class Text(val text: String, val conversationId: ConversationId) : OutcomeMessage()