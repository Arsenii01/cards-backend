package ru.musailov.cards.service

import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.musailov.cards.dto.*
import ru.musailov.cards.exception.NotFoundException
import ru.musailov.cards.model.CardEntity
import ru.musailov.cards.model.CardLinkEntity
import ru.musailov.cards.model.toEntity
import ru.musailov.cards.repository.*
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrElse
import kotlin.random.Random

@Service
class CardService(
    private val cardRepository: CardRepository,
    private val cardLinkRepository: CardLinkRepository,
    private val userRepository: UserRepository,
    private val socialNetworkRepository: SocialNetworkRepository,
    private val companyInfoRepository: CompanyInfoRepository
) {

    fun getCards(): List<CardListDto> {
        val userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        val user = userRepository.findByEmail(userEmail as String)
        return user?.cards?.map { it.toListDto()}!!
    }

    @Transactional
    fun createCard(cardDto: CardDto) {
        val userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        val user = userRepository.findByEmail(userEmail as String)
        val card = cardDto.toEntity()
        card.user = user

        val cardLink = CardLinkEntity(generateCardId(), card)
        val socialNetworks = cardDto.socialNetworks?.map { it.toEntity(card) }
        val companyInfo = cardDto.companyInfo?.toEntity(card)
        cardRepository.save(card)
        cardLinkRepository.save(cardLink)
        if (companyInfo != null)
            companyInfoRepository.save(companyInfo)
        if (socialNetworks != null)
            socialNetworkRepository.saveAll(socialNetworks)
    }

    @Transactional
    fun editCard(cardId: Long, cardDto: CardDto) {
        val card = cardRepository.findById(cardId).getOrElse { throw NotFoundException("Визитка не существует") }

        val mergedCard = card.mergeForUpdate(cardDto)

        if (card.companyInfoEntity != null)
            companyInfoRepository.delete(card.companyInfoEntity!!)
        socialNetworkRepository.deleteAllByCard(card)

        val socialNetworks = cardDto.socialNetworks?.map { it.toEntity(card)}
        val companyInfo = cardDto.companyInfo?.toEntity(card)
        cardRepository.save(mergedCard)
        if (socialNetworks != null)
            socialNetworkRepository.saveAll(socialNetworks)
        if (companyInfo != null)
            companyInfoRepository.save(companyInfo)
    }

    fun deleteCard(cardId: Long) {
        cardRepository.deleteById(cardId)
    }

    fun getCardById(cardId: Long): CardDto {
        val card = cardRepository.findById(cardId).getOrElse { throw NotFoundException("Визитка не существует") }

        return card.toDto()
    }

    fun CardEntity.mergeForUpdate(cardDto: CardDto): CardEntity {
        return CardEntity(
            cardId = this.cardId,
            cardName = cardDto.cardName,
            fullName = cardDto.fullName,
            surname = cardDto.surname,
            position = cardDto.position,
            phoneNumber = cardDto.phoneNumber,
            email = cardDto.email,
            degree = cardDto.degree,
            aboutMe = cardDto.aboutMe,
            address = cardDto.address,
            user = this.user,
            cardLink = this.cardLink,
            companyInfoEntity = null,
            createdDate = this.createdDate,
            modifiedDate = LocalDateTime.now()
        )
    }
//    private fun generateCardLanding(cardEntity: CardEntity) {
//        val context = createContext(cardEntity)
//        val htmlContent = templateEngine.process("card-template", context)
//
//        val path = Paths.get(Paths.get("").toAbsolutePath().toString() + LANDING_PATH + "card-${cardEntity.cardId}.html")
//        Files.write(path, htmlContent.toByteArray())
//    }
//    private fun createContext(cardEntity: CardEntity) : Context {
//        val context = Context().apply {
//            setVariable("fullName", cardEntity.fullName)
//            setVariable("surname", cardEntity.surname)
//        }
//        return context
//    }

    private fun generateCardId(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..8)
            .map { Random.nextInt(charset.length) }
            .map(charset::get)
            .joinToString("")
    }
}