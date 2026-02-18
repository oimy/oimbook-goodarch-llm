package oimbook.goodarch.app.chat.payloads

data class TalkMessagePayload(
    val contextId: Long?,
    val content: String
)