package ru.musailov.cards.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "social_network")
data class SocialNetwork(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val recordId: Long? = null,
    @Column(nullable = false)
    val name: String? = null,
    @Column(nullable = false)
    val link: String? = null,
    val createdDate: LocalDateTime? = LocalDateTime.now(),
    val modifiedDate: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id", nullable = false)
    val card: CardEntity?
)
