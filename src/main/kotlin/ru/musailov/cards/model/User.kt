package ru.musailov.cards.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long? = null,

    @Column(unique = true)
    val email: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val cards: List<CardEntity> = emptyList(),

    val createdDate: LocalDateTime? = LocalDateTime.now(),

    val modifiedDate: LocalDateTime? = LocalDateTime.now()
)
