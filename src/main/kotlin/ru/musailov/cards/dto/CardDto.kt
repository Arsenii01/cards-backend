package ru.musailov.cards.dto

import ru.musailov.cards.model.CardEntity

data class CardDto(
    val cardId: Long? = null,
    val cardName: String? = null,
    val fullName: String? = null,
    val surname: String? = null,
    val position: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val degree: String? = null,
    val aboutMe: String? = null,
    val address: String? = null,
    val socialNetworks: List<SocialNetworkDto>? = null,
    val companyInfo: CompanyInfoDto? = null,
    val landingId: String? = null
)

fun CardEntity.toDto() =
    CardDto(
        cardId = this.cardId,
        cardName = this.cardName,
        fullName = this.fullName,
        surname = this.surname,
        position = this.position,
        phoneNumber = this.phoneNumber,
        email = this.email,
        degree = this.degree,
        aboutMe = this.aboutMe,
        address = this.address,
        landingId = this.cardLink!!.landingId,
        socialNetworks = this.socialNetworks?.map { it.toDto() },
        companyInfo = this.companyInfoEntity?.toDto()
    )