package ru.musailov.cards.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.musailov.cards.dto.CardDto
import ru.musailov.cards.dto.CardListDto
import ru.musailov.cards.service.CardService

@RestController
@RequestMapping("/card")
@Tag(name = "CardController", description = "Контроллер для управления визитками")
class CardController(
    private val cardService: CardService
) {

    @Operation(summary = "Получить список всех визиток")
    @ApiResponse(
        responseCode = "200",
        description = "Список визиток успешно получен",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = CardListDto::class))]
    )
    @GetMapping
    fun getCards(): List<CardListDto> {
        return cardService.getCards()
    }

    @Operation(summary = "Получить визитку по ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Карточка найдена"),
        ApiResponse(responseCode = "404", description = "Карточка не найдена")
    ])
    @GetMapping("/{id}")
    fun getCardById(
        @PathVariable("id") cardId: Long
    ): CardDto? {
        return cardService.getCardById(cardId)
    }

    @Operation(summary = "Создать новую визитку")
    @ApiResponse(responseCode = "200", description = "Карточка успешно создана")
    @PostMapping
    fun createCard(
        @RequestBody card: CardDto
    ) {
        cardService.createCard(card)
    }

    @Operation(summary = "Редактировать существующую визитку")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Карточка успешно отредактирована"),
        ApiResponse(responseCode = "404", description = "Карточка не найдена")
    ])
    @PostMapping("/{id}")
    fun editCard(
        @PathVariable("id") cardId: Long,
        @RequestBody card: CardDto
    ) {
        cardService.editCard(cardId, card)
    }

    @Operation(summary = "Удалить визитку")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Карточка успешно удалена"),
        ApiResponse(responseCode = "404", description = "Карточка не найдена")
    ])
    @DeleteMapping("/{id}")
    fun deleteCard(
        @PathVariable("id") cardId: Long
    ) {
        cardService.deleteCard(cardId)
    }
}