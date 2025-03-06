package ru.musailov.cards.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.musailov.cards.dto.CardDto
import ru.musailov.cards.dto.GenerateTextResponseDto
import ru.musailov.cards.service.TextRecommendService

@RestController
@RequestMapping("/recommend")
class RecommendationController(
    private val recommendService: TextRecommendService
) {

    @PostMapping("/generate-about-me")
    fun generateAboutMe(@RequestBody cardDto: CardDto): GenerateTextResponseDto? {
        return recommendService.generateText(cardDto)
    }
}