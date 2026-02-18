package oimbook.goodarch.infra.database.chat.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class Role(@JsonValue val value: String) {
    USER("user"),
    ASSISTANT("assistant"),
}