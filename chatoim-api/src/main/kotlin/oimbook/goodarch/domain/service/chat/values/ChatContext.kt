package oimbook.goodarch.domain.service.chat.values

import java.time.LocalDateTime

data class ChatContext(
    val srl: Long,
    val title: String,
    val createdAt: LocalDateTime,
)
