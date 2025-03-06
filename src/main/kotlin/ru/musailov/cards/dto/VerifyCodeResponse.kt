package ru.musailov.cards.dto

data class VerifyCodeResponse(
    val token: String? = null,
    val errorMessage: String? = null
)
