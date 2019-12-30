package com.turbomates.corebot.dialogue

import com.turbomates.corebot.incomeactivity.ConversationId
import java.util.*

class Dialogue (conversationId: ConversationId, private val messages: List<DialogueMessage>) {

}

data class DialogueMessage(val message: String) {
    val id = DialogueMessageId()
}

class DialogueMessageId {
    val id:UUID = UUID.randomUUID()
}