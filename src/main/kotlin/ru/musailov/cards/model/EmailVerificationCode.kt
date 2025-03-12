package ru.musailov.cards.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "email_verification_code")
data class EmailVerificationCode(
    @Id
    val email: String,

    @Column(nullable = false)
    val code: Int,

    val createdDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val expires: LocalDateTime
)
