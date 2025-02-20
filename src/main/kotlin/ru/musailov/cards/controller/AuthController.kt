package ru.musailov.cards.controller
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.musailov.cards.service.AuthService

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    /**
     * Отправка кода подтверждения на указанный email.
     */
    @PostMapping("/send-code")
    fun sendCode(@RequestParam email: String): ResponseEntity<Map<String, String>> {
        authService.sendVerificationCode(email)
        return ResponseEntity.ok(mapOf("message" to "Код отправлен на email"))
    }

    /**
     * Верификация кода. При успехе возвращается JWT-токен.
     */
    @PostMapping("/verify")
    fun verify(
        @RequestParam email: String,
        @RequestParam code: String
    ): ResponseEntity<Map<String, String>> {
        val token = authService.verifyCode(email, code)
        return if (token != null) {
            ResponseEntity.ok(mapOf("token" to token))
        } else {
            ResponseEntity.badRequest().body(mapOf("error" to "Неверный или истёкший код подтверждения"))
        }
    }
}
