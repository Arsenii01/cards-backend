package ru.musailov.cards.service

import jakarta.transaction.Transactional
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import ru.musailov.cards.auth.JwtUtil
import ru.musailov.cards.dto.SendVerificationCodeResponse
import ru.musailov.cards.model.EmailVerificationCode
import ru.musailov.cards.model.User
import ru.musailov.cards.repository.EmailVerificationCodeRepository
import ru.musailov.cards.repository.UserRepository
import java.time.LocalDateTime
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val emailCodeRepo: EmailVerificationCodeRepository,
    private val mailSender: JavaMailSender,
    private val jwtUtil: JwtUtil
) {

    @Transactional
    fun sendVerificationCode(email: String): SendVerificationCodeResponse {
        val code = UUID.randomUUID().toString().substring(0, 6) // например, 6-символьный код
        val user = userRepository.findByEmail(email) ?: User(email = email)
        emailCodeRepo.deleteByEmail(email)
        val verificationCode = EmailVerificationCode(email, code, expires = LocalDateTime.now().plusMinutes(15))
        userRepository.save(user)
        emailCodeRepo.save(verificationCode)
        sendEmail(email, code)
        return SendVerificationCodeResponse(email, "Код подтверждения отправлен на указанный email")
    }

    private fun sendEmail(email: String, code: String) {
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
    fun verifyCode(email: String, codeFromUser: String): String? {
        val emailVerificationCode = emailCodeRepo.findByEmail(email) ?: return null
        if (emailVerificationCode.code == codeFromUser && emailVerificationCode.expires.isAfter(LocalDateTime.now())) {
            emailCodeRepo.delete(emailVerificationCode)
            return jwtUtil.generateToken(email)
        }
        return null
    }
}
