package oimbook.goodarch.domain.service.chat.values

import oimbook.goodarch.infra.database.chat.enums.Role

data class ChatMessageSave(
    val role: Role,
    val content: String,
)

