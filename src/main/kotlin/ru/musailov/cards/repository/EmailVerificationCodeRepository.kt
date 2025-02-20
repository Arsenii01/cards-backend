package ru.musailov.cards.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.musailov.cards.model.EmailVerificationCode

interface EmailVerificationCodeRepository: JpaRepository<EmailVerificationCode, String> {
    fun findByEmail(email: String): EmailVerificationCode?

    fun deleteByEmail(email: String)
}