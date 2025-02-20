package ru.musailov.cards.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "card_link")
class CardLinkEntity(
    @Id
    @Column(nullable = false)
    val landingId: String? = null,
    @OneToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id")
    val card: CardEntity,
    val createdDate: LocalDateTime? = LocalDateTime.now()
)