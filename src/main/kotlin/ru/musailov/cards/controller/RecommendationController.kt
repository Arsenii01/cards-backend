package ru.musailov.cards.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.musailov.cards.dto.CardDto
import ru.musailov.cards.dto.GenerateTextResponseDto
import ru.musailov.cards.service.TextRecommendService

@RestController
@RequestMapping("/recommend")
@Tag(name = "RecommendationController", description = "Контроллер для генерации рекомендаций текста")
class RecommendationController(
    private val recommendService: TextRecommendService
) {

    @Operation(
        summary = "Сгенерировать текст 'О себе'",
        description = "Генерирует рекомендуемый текст для раздела 'О себе' на основе данных визитки"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Текст успешно сгенерирован",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = GenerateTextResponseDto::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Некорректные данные визитки"
        )
    ])
    @PostMapping("/generate-about-me")
    fun generateAboutMe(
        @RequestBody cardDto: CardDto
    ): GenerateTextResponseDto? {
        return recommendService.generateText(cardDto)
    }
}