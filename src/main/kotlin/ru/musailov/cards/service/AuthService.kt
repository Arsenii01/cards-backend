package ru.musailov.cards.service

import jakarta.transaction.Transactional
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import ru.musailov.cards.auth.JwtUtil
import ru.musailov.cards.dto.SendVerificationCodeResponse
import ru.musailov.cards.dto.VerifyCodeResponse
import ru.musailov.cards.model.EmailVerificationCode
import ru.musailov.cards.model.User
import ru.musailov.cards.repository.EmailVerificationCodeRepository
import ru.musailov.cards.repository.UserRepository
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val emailCodeRepo: EmailVerificationCodeRepository,
    private val mailSender: JavaMailSender,
    private val jwtUtil: JwtUtil
) {

    @Transactional
    fun sendVerificationCode(email: String): SendVerificationCodeResponse {
        val code = Random.nextInt(100000, 999999)
        val user = userRepository.findByEmail(email) ?: User(email = email)
        emailCodeRepo.deleteByEmail(email)
        val verificationCode = EmailVerificationCode(
            email,
            code,
            expires = LocalDateTime.now().plusMinutes(15))
        userRepository.save(user)
        emailCodeRepo.save(verificationCode)
        sendEmail(email, code)
        return SendVerificationCodeResponse(email, "Код подтверждения отправлен на указанный email")
    }

    private fun sendEmail(email: String, code: Int) {
        val message = SimpleMailMessage()
        message.setTo(email)
        message.subject = "Ваш код подтверждения в QRCardsApp"
        message.text = "Введите следующий код в приложении для входа: $code"
        mailSender.send(message)
    }

    /**
     * Проверяет введённый код для указанного email. Если код корректный, помечает пользователя как верифицированного и возвращает JWT.
     */
    @Transactional
    fun verifyCode(email: String, codeFromUser: Int): VerifyCodeResponse {
        val emailVerificationCode = emailCodeRepo.findByEmail(email) ?: return VerifyCodeResponse("Код подтверждения неверный")
        if (emailVerificationCode.code == codeFromUser) {
            if (emailVerificationCode.expires.isAfter(LocalDateTime.now())) {
                emailCodeRepo.delete(emailVerificationCode)
                return VerifyCodeResponse(jwtUtil.generateToken(email))
            } else {
                return VerifyCodeResponse(errorMessage = "Код подтверждения истёк")
            }
        }
        return VerifyCodeResponse(errorMessage = "Код подтверждения неверный")
    }
}
