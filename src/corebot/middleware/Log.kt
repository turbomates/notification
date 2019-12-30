package com.turbomates.corebot.middleware

import mu.KLogger

class Log(private val logger: KLogger): AfterSend {
    override fun invoke(link: ExternalIdLink) {
        logger.info("Message ${link.messageId.id} was sent to conversation ${link.conversationId.id}. External id: ${link.externalId.id}")
    }
}