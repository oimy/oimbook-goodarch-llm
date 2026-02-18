package oimbook.goodarch.domain.service.chat

import oimbook.goodarch.domain.service.chat.values.ChatContext
import oimbook.goodarch.domain.service.chat.values.ChatMessage
import oimbook.goodarch.domain.service.chat.values.ChatMessageSave

interface ChatContextService {

    fun create(content: String): Long

    fun findAll(): List<ChatContext>

    fun findMessagesOrderByCreatedAt(contextId: Long): List<ChatMessage>

    fun addMessages(contextId: Long, messages: Collection<ChatMessageSave>)

}