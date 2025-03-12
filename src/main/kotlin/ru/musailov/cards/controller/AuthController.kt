package ru.musailov.cards.controller
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.musailov.cards.dto.SendVerificationCodeRequest
import ru.musailov.cards.dto.SendVerificationCodeResponse
import ru.musailov.cards.dto.VerifyCodeRequest
import ru.musailov.cards.dto.VerifyCodeResponse
import ru.musailov.cards.service.AuthService

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Контроллер для аутентификации и верификации пользователей")
class AuthController(
    private val authService: AuthService
) {

    /**
     * Отправка кода подтверждения на указанный email.
     */
    @PostMapping("/send-code")
    @Operation(
        summary = "Отправка кода подтверждения",
        description = "Отправляет код подтверждения на указанный email."
    )
    fun sendCode(@RequestBody request: SendVerificationCodeRequest): SendVerificationCodeResponse {
        return authService.sendVerificationCode(request.email)
    }

    /**
     * Верификация кода. При успехе возвращается JWT-токен.
     */
    @Operation(
        summary = "Верификация кода",
        description = "Проверяет код подтверждения и возвращает JWT-токен при успешной верификации."
    )
    @PostMapping("/verify")
    fun verify(
       @RequestBody request: VerifyCodeRequest
    ): VerifyCodeResponse {
        return authService.verifyCode(request.email, request.code)
    }
}
