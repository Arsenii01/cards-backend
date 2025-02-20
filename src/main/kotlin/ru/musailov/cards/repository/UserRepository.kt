package ru.musailov.cards.repository

import org.springframework.data.repository.CrudRepository
import ru.musailov.cards.model.User

interface UserRepository: CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
}