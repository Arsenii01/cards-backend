package ru.musailov.cards.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class CardsLimitForUserException(msg: String) : Exception(msg)