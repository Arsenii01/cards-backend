package ru.musailov.cards.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.musailov.cards.model.CardLinkEntity

interface CardLinkRepository: JpaRepository<CardLinkEntity, String> {
}