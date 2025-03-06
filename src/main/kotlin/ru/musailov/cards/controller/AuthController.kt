package ru.musailov.cards.controller
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.musailov.cards.dto.SendVerificationCodeRequest
import ru.musailov.cards.dto.SendVerificationCodeResponse
import ru.musailov.cards.dto.VerifyCodeRequest
import ru.musailov.cards.dto.VerifyCodeResponse
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
    fun sendCode(@RequestBody request: SendVerificationCodeRequest): SendVerificationCodeResponse {
        return authService.sendVerificationCode(request.email)
    }

    /**
     * Верификация кода. При успехе возвращается JWT-токен.
     */
    @PostMapping("/verify")
    fun verify(
       @RequestBody request: VerifyCodeRequest
    ): VerifyCodeResponse {
        val token = authService.verifyCode(request.email, request.code)
        return if (token != null) {
            VerifyCodeResponse(token = token)
        } else {
            VerifyCodeResponse(errorMessage = "Код подтверждения неверный или его срок действия истёк")
        }
    }
}
