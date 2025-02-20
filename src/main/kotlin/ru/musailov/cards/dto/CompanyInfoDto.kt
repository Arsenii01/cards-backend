package ru.musailov.cards.dto

import ru.musailov.cards.model.CompanyInfoEntity

data class CompanyInfoDto(
    val companyName: String? = null,
    val businessLine: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val companyWebsite: String? = null
)

fun CompanyInfoEntity.toDto() =
    CompanyInfoDto(
        companyName = this.companyName,
        businessLine = this.businessLine,
        phoneNumber = this.phoneNumber,
        email = this.email,
        companyWebsite = this.companyWebsite
    )