package oimbook.goodarch.domain.service.chat

import oimbook.goodarch.domain.service.chat.values.ChatMessage
import oimbook.goodarch.domain.service.chat.values.ChatMessageSave
import oimbook.goodarch.infra.database.chat.enums.Role
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Service
class ChatTalkServiceImpl(
    private val chatBridgeService: ChatBridgeService,
    private val chatContextService: ChatContextService
) :
    ChatTalkService {

    private fun talkAndSave(contextId: Long, messages: List<ChatMessage>, userContent: String?): Flux<String> {
        val assistantBuffer = StringBuilder()

        return chatBridgeService.talk(messages)
            .doOnNext { chunk ->
                assistantBuffer.append(chunk)
            }
            .doOnComplete {
                val newMessages = mutableListOf<ChatMessageSave>()
                if (userContent != null) {
                    newMessages.add(ChatMessageSave(role = Role.USER, content = userContent))
                }
                newMessages.add(ChatMessageSave(role = Role.ASSISTANT, content = assistantBuffer.toString()))

                chatContextService.addMessages(contextId = contextId, messages = newMessages)
            }
    }

    override fun talk(contextId: Long, content: String): Flux<String> {
        val messageHistories: List<ChatMessage> = chatContextService.findMessagesOrderByCreatedAt(contextId)
        if (messageHistories.isNotEmpty() && messageHistories.last().role == Role.USER) {
            return talkAndSave(contextId = contextId, messages = messageHistories, userContent = null)
        }

        val messages: MutableList<ChatMessage> = messageHistories.toMutableList()
        messages.add(ChatMessage(role = Role.USER, content = content, createdAt = LocalDateTime.now()))

        return talkAndSave(contextId = contextId, messages = messages, userContent = content)
    }

}