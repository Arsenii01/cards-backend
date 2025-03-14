package ru.musailov.cards.model

data class GenerateTextResponse(
    val result: Result
)

data class Result(
    val alternatives: List<Alternative>,
    val usage: Usage,
    val modelVersion: String?
)

data class Alternative(
    val message: Message,
    val status: String?
)

data class Usage(
    val inputTextTokens: String,
    val completionTokens: String,
    val totalTokens: String
)

