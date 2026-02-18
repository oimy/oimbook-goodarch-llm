package oimbook.goodarch.app.chat.payloads

import java.time.LocalDateTime

data class ChatContextPayload(
    val srl: Long,
    val title: String,
    val createdAt: LocalDateTime,
)