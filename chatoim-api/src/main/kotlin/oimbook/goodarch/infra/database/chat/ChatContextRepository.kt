package oimbook.goodarch.infra.database.chat

import org.springframework.data.jpa.repository.JpaRepository

interface ChatContextRepository : JpaRepository<ChatContextEntity, Long>