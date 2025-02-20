package ru.musailov.cards.controller

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.musailov.cards.exception.NotFoundException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to e.message!!))
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleJwtExpired(e: ExpiredJwtException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to "JWT token expired"))
    }

}