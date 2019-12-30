package com.turbomates.corebot.middleware

import com.turbomates.corebot.conversation.storage.Storage

class ReverseLinking(private val storage: Storage): AfterSend
{
    override fun invoke(link: ExternalIdLink) {
        storage.get(link.conversationId).messageExternalLink(link.messageId, link.externalId)
    }
}
