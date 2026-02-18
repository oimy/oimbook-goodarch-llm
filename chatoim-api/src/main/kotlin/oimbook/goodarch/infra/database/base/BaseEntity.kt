package oimbook.goodarch.infra.database.base

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val srl: Long = 0L,
) :
    Entity {

    lateinit var createdAt: LocalDateTime
    lateinit var modifiedAt: LocalDateTime

    @PrePersist
    override fun create() {
        this.createdAt = LocalDateTime.now()
        this.modifiedAt = createdAt
    }

    @PreUpdate
    override fun modify() {
        this.modifiedAt = LocalDateTime.now()
    }

}