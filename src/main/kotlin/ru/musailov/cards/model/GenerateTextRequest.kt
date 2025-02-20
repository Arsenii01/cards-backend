package ru.musailov.cards.model

data class GenerateTextRequest(
    val modelUri: String,
    val completionOptions: CompletionOptions,
    val messages: List<Message>?
)


data class CompletionOptions(
    val stream: Boolean?,
    val temperature: Float?,
    val maxTokens: String? = null,
    val reasoningOptions: ReasoningOption?,

)

data class ReasoningOption(
    val mode: String? = null
)

data class Message(
    val role: String? = null,
    val text: String? = null
)