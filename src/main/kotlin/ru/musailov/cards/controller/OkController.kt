package ru.musailov.cards.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OkController {

    @GetMapping("/ok")
    fun ok() = "ok"
}