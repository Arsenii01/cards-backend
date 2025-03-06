package ru.musailov.cards.model

import jakarta.persistence.*
import ru.musailov.cards.dto.CompanyInfoDto
import java.time.LocalDateTime

@Entity
@Table(name = "company_info")
data class CompanyInfoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val recordId: Long? = null,

    val companyName: String? = null,
    val businessLine: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val companyWebsite: String? = null,
    val address: String? = null,

    @OneToOne
    @JoinColumn(name = "card_id", referencedColumnName = "card_id")
    val card: CardEntity? = null,

    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)

fun CompanyInfoDto.toEntity(card: CardEntity): CompanyInfoEntity {
    return CompanyInfoEntity(
        card = card,
        companyName = this.companyName,
        businessLine = this.businessLine,
        phoneNumber = this.phoneNumber,
        email = this.email,
        companyWebsite = this.companyWebsite,
        address = this.address
    )
}
