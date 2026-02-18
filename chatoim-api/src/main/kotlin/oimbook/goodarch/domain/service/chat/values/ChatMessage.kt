package oimbook.goodarch.domain.service.chat.values

import oimbook.goodarch.infra.database.chat.enums.Role
import java.time.LocalDateTime

data class ChatMessage(
    val role: Role,
    val content: String,
    val createdAt: LocalDateTime,
)

