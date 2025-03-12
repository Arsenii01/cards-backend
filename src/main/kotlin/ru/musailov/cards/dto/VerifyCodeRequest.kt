package ru.musailov.cards.dto

data class VerifyCodeRequest(
    val email: String,
    val code: Int
)
