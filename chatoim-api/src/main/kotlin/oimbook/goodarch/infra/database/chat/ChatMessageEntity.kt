package oimbook.goodarch.infra.database.chat

import jakarta.persistence.*
import oimbook.goodarch.infra.database.base.BaseEntity
import oimbook.goodarch.infra.database.chat.enums.Role

@Entity
@Table(name = "chat_messages")
class ChatMessageEntity private constructor(
    @ManyToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    @JoinColumn(name = "contextId", nullable = false)
    val context: ChatContextEntity,

    @Enumerated(value = EnumType.STRING)
    val role: Role,

    @Column(columnDefinition = "TEXT")
    val content: String,
) :
    BaseEntity() {

    companion object {
        fun of(context: ChatContextEntity, role: Role, content: String) =
            ChatMessageEntity(context = context, role = role, content = content)
    }

}