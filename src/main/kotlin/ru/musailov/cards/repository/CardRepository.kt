package ru.musailov.cards.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.musailov.cards.model.CardEntity
import ru.musailov.cards.model.User

interface CardRepository: JpaRepository<CardEntity, Long>