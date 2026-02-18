package oimbook.goodarch.domain.service.chat.values

import oimbook.goodarch.infra.database.chat.enums.Role

data class ChatMessageBody(
    val role: Role,
    val content: String
)

