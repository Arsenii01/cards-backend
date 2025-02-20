package ru.musailov.cards.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.musailov.cards.model.CardEntity
import ru.musailov.cards.model.SocialNetwork

interface SocialNetworkRepository: JpaRepository<SocialNetwork, Long> {
    fun deleteAllByCard(cardEntity: CardEntity)
}