package ru.musailov.cards.repository

import org.springframework.data.repository.CrudRepository
import ru.musailov.cards.model.CardEntity
import ru.musailov.cards.model.CompanyInfoEntity

interface CompanyInfoRepository: CrudRepository<CompanyInfoEntity, Long> {
    fun deleteByCard(card: CardEntity)
}