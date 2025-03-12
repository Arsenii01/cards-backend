package ru.musailov.cards.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ru.musailov.cards.service.CardLandingService

@Controller
@RequestMapping("/card-landing")
@Tag(name = "CardLandingController", description = "Контроллер для отображения лендинга визитки")
class CardLandingController(
    private val cardLandingService: CardLandingService
) {

    @Operation(summary = "Получить HTML страницу лендинга визитки")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "HTML страница успешно сгенерирована"),
        ApiResponse(responseCode = "404", description = "Лендинг не найден")
    ])
    @GetMapping("/{id}", produces = ["text/html;charset=UTF-8"])
    fun getCardLandingPage(
        @PathVariable("id") landingId: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(cardLandingService.generateLanding(landingId))
    }

}