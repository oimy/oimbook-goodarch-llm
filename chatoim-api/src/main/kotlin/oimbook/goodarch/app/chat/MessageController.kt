package oimbook.goodarch.app.chat

import oimbook.goodarch.app.chat.payloads.ChatMessagePayload
import oimbook.goodarch.domain.service.chat.ChatContextService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("ChatMessageController")
@RequestMapping("/chat/messages")
class MessageController(
    private val chatContextService: ChatContextService
) {

    @GetMapping
    fun findAll(@RequestParam contextId: Long): List<ChatMessagePayload> =
        chatContextService.findMessagesOrderByCreatedAt(contextId).map {
            ChatMessagePayload(role = it.role, content = it.content, createdAt = it.createdAt)
        }


}