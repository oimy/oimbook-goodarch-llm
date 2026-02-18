package oimbook.goodarch.app.chat

import oimbook.goodarch.app.chat.payloads.TalkMessagePayload
import oimbook.goodarch.domain.service.chat.ChatContextService
import oimbook.goodarch.domain.service.chat.ChatTalkService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import reactor.core.publisher.Flux

@RestController("ChatTalkController")
@RequestMapping("/chat/talk")
class TalkController(
    private val chatContextService: ChatContextService,
    private val chatTalkService: ChatTalkService
) {

    @PostMapping
    fun talk(
        @RequestBody talkMessage: TalkMessagePayload
    ): ResponseEntity<StreamingResponseBody> {
        val contextId: Long = talkMessage.contextId
            ?: chatContextService.create(content = talkMessage.content)

        val contentFlux: Flux<String> = chatTalkService.talk(contextId = contextId, content = talkMessage.content)
        val responseBody = StreamingResponseBody { outputStream ->
            contentFlux
                .toIterable().forEach {
                outputStream.write(it.toByteArray())
                outputStream.flush()
            }
        }

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE + ";charset=UTF-8")
            .header(HttpHeaders.CACHE_CONTROL, "no-cache")
            .body(responseBody)
    }

}