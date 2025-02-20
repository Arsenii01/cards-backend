package ru.musailov.cards.dto

import ru.musailov.cards.model.CardEntity

data class CardListDto(
    val cardId: Long? = null,
    val cardName: String? = null,
    val landingId: String? = null,
)

fun CardEntity.toListDto() =
    CardListDto(
        cardId = this.cardId,
        cardName = this.cardName,
        landingId = this.cardLink!!.landingId
    )
