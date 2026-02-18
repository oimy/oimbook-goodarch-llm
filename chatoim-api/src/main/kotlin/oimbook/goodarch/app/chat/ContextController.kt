package oimbook.goodarch.app.chat

import oimbook.goodarch.app.chat.payloads.ChatContextPayload
import oimbook.goodarch.app.chat.payloads.TalkMessagePayload
import oimbook.goodarch.domain.service.chat.ChatContextService
import org.springframework.web.bind.annotation.*

@RestController("ChatContextController")
@RequestMapping("/chat/contexts")
class ContextController(
    private val chatContextService: ChatContextService
) {

    @PostMapping
    fun save(@RequestBody message: TalkMessagePayload): Long =
        chatContextService.create(content = message.content)

    @GetMapping
    fun findAll(): List<ChatContextPayload> =
        chatContextService.findAll()
            .map { ChatContextPayload(srl = it.srl, title = it.title, createdAt = it.createdAt) }


}