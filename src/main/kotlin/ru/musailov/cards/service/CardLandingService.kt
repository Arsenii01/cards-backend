package ru.musailov.cards.service

import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.musailov.cards.exception.NotFoundException
import ru.musailov.cards.model.CardEntity
import ru.musailov.cards.repository.CardLinkRepository
import kotlin.jvm.optionals.getOrElse

@Service
class CardLandingService(
    private val cardLinkRepository: CardLinkRepository,
    private val templateEngine: SpringTemplateEngine
) {

    fun generateLanding(landingId: String): String {
        val cardLink = cardLinkRepository.findById(landingId).getOrElse {
            throw NotFoundException("Ссылка невалидна")
        }
        val card = cardLink.card

        val htmlContent = generateCardLanding(card)
        return htmlContent
    }

    private fun generateCardLanding(cardEntity: CardEntity): String {
        val context = createContext(cardEntity)
        val htmlContent = templateEngine.process("card-template3", context)

        return htmlContent
    }
    private fun createContext(cardEntity: CardEntity) : Context {
        val context = Context().apply {
            setVariable("fullName", cardEntity.fullName)
            setVariable("surname", cardEntity.surname)
            setVariable("position", cardEntity.position)
            setVariable("phoneNumber", cardEntity.phoneNumber)
            setVariable("email", cardEntity.email)
            setVariable("degree", cardEntity.degree)
            setVariable("aboutMe", cardEntity.aboutMe)
            setVariable("address", cardEntity.address)
            setVariable("socialNetworks", cardEntity.socialNetworks)
            setVariable("companyInfo", cardEntity.companyInfoEntity)
        }
        return context
    }

}