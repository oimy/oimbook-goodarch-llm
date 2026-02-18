package oimbook.goodarch.app.chat.payloads

import oimbook.goodarch.infra.database.chat.enums.Role
import java.time.LocalDateTime

data class ChatMessagePayload(
    val role: Role,
    val content: String,
    val createdAt: LocalDateTime,
)