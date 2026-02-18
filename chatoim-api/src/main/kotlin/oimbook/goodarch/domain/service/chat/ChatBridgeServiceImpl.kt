package oimbook.goodarch.domain.service.chat

import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import oimbook.goodarch.domain.service.chat.values.ChatMessage
import oimbook.goodarch.domain.utils.log.Logging
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

@Service
class ChatBridgeServiceImpl : ChatBridgeService {

    companion object : Logging() {
        const val BRIDGE_URL = "http://localhost:8000/inference/midm"
    }

    private val httpClient = HttpClient.create()
        .responseTimeout(Duration.ofMinutes(10))
        .doOnConnected { conn ->
            conn.addHandlerLast(ReadTimeoutHandler(60, TimeUnit.SECONDS)) // 데이터 패킷 간 최대 간격
            conn.addHandlerLast(WriteTimeoutHandler(60, TimeUnit.SECONDS))
        }
    private val webClient: WebClient = WebClient.builder()
        .baseUrl(BRIDGE_URL)
        .clientConnector(ReactorClientHttpConnector(httpClient))
        .build()

    override fun talk(messages: List<ChatMessage>): Flux<String> =
        webClient.post()
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .bodyValue(messages.sortedBy { it.createdAt })
            .retrieve()
            .bodyToFlux<DataBuffer>()
            .timeout(Duration.ofSeconds(300))
            .mapNotNull { buffer ->
                val bytes = ByteArray(buffer.readableByteCount())
                buffer.read(bytes)
                DataBufferUtils.release(buffer)
                String(bytes, Charsets.UTF_8)
            }

}