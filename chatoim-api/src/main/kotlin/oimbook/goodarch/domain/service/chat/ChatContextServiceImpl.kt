package oimbook.goodarch.domain.service.chat

import oimbook.goodarch.domain.service.chat.values.ChatContext
import oimbook.goodarch.domain.service.chat.values.ChatMessage
import oimbook.goodarch.domain.service.chat.values.ChatMessageSave
import oimbook.goodarch.infra.database.chat.ChatContextEntity
import oimbook.goodarch.infra.database.chat.ChatContextRepository
import oimbook.goodarch.infra.database.chat.ChatMessageEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatContextServiceImpl(
    private val contextRepository: ChatContextRepository
) :
    ChatContextService {

    companion object {
        const val MAX_LENGTH_TITLE = 15
    }

    @Transactional
    override fun create(content: String): Long {
        var title = content.substring(0, minOf(MAX_LENGTH_TITLE, content.length))
        if (content.length > MAX_LENGTH_TITLE) {
            title += "..."
        }

        val context = ChatContextEntity.of(title = title)

        val savedContext = contextRepository.save(context)
        return savedContext.srl
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<ChatContext> =
        contextRepository.findAll().map { ChatContext(srl = it.srl, title = it.title, createdAt = it.createdAt) }

    @Transactional(readOnly = true)
    override fun findMessagesOrderByCreatedAt(contextId: Long): List<ChatMessage> {
        val context = contextRepository.findByIdOrNull(contextId)
            ?: return emptyList()

        return context.messages
            .sortedBy { it.createdAt }
            .map { ChatMessage(role = it.role, content = it.content, createdAt = it.createdAt) }
    }

    @Transactional
    override fun addMessages(contextId: Long, messages: Collection<ChatMessageSave>) {
        val context = contextRepository.findByIdOrNull(contextId)
            ?: throw IllegalArgumentException("No chat context found with id $contextId")

        context.messages.addAll(messages.map {
            ChatMessageEntity.of(context = context, role = it.role, content = it.content)
        })
        contextRepository.save(context)
    }

}