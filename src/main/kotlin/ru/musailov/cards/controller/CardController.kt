package ru.musailov.cards.controller

import org.springframework.web.bind.annotation.*
import ru.musailov.cards.dto.CardDto
import ru.musailov.cards.dto.CardListDto
import ru.musailov.cards.service.CardService

@RestController
@RequestMapping("/card")
class CardController(
    private val cardService: CardService
) {

    @GetMapping
    fun getCards(): List<CardListDto> {
        return cardService.getCards()
    }

    @GetMapping("/{id}")
    fun getCardById(@PathVariable("id") cardId: Long): CardDto? {
        return cardService.getCardById(cardId)
    }

    @PostMapping
    fun createCard(@RequestBody card: CardDto) {
        cardService.createCard(card)
    }

    @PostMapping("/{id}")
    fun editCard(@PathVariable("id") cardId: Long, @RequestBody card: CardDto) {
        cardService.editCard(cardId, card)
    }

    @DeleteMapping("/{id}")
    fun deleteCard(@PathVariable("id") cardId: Long) {
        cardService.deleteCard(cardId)
    }
}