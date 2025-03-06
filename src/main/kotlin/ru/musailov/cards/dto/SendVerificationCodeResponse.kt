package ru.musailov.cards.dto

data class SendVerificationCodeResponse(
    val email: String,
    val message: String
)
