package oimbook.goodarch.domain.service.chat

import reactor.core.publisher.Flux

fun interface ChatTalkService {

    fun talk(contextId: Long, content: String): Flux<String>

}