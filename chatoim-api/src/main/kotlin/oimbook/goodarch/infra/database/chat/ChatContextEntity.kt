package oimbook.goodarch.infra.database.chat

import jakarta.persistence.*
import oimbook.goodarch.infra.database.base.BaseEntity

@Entity
@Table(name = "chat_contexts")
class ChatContextEntity private constructor(
    val title: String,

    @OneToMany(mappedBy = "context", fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], orphanRemoval = true)
    val messages: MutableList<ChatMessageEntity> = mutableListOf()
) :
    BaseEntity() {

    companion object {
        fun of(title: String) =
            ChatContextEntity(title = title)
    }

}