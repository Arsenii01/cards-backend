package ru.musailov.cards.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ru.musailov.cards.service.CardLandingService

@Controller
@RequestMapping("/card-landing")
class CardLandingController(
    private val cardLandingService: CardLandingService
) {

    @GetMapping("/{id}", produces = ["text/html;charset=UTF-8"])
    fun getCardLandingPage(@PathVariable("id") landingId: String): ResponseEntity<String> {
        return ResponseEntity.ok(cardLandingService.generateLanding(landingId))
    }

}