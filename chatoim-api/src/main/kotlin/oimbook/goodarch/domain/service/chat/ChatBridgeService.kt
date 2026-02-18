package oimbook.goodarch.domain.service.chat

import oimbook.goodarch.domain.service.chat.values.ChatMessage
import reactor.core.publisher.Flux

fun interface ChatBridgeService {

    fun talk(messages: List<ChatMessage>): Flux<String>

}