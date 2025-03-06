package ru.musailov.cards.model

import jakarta.persistence.*
import ru.musailov.cards.dto.CardDto
import java.time.LocalDateTime

@Entity
@Table(name = "card")
data class CardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    val cardId: Long? = null,
    @Column(nullable = false)
    val cardName: String? = null,
    @Column(nullable = false)
    val fullName: String? = null,
    val surname: String? = null,
    val position: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val degree: String? = null,
    val aboutMe: String? = null,
    val address: String? = null,
    val createdDate: LocalDateTime? = LocalDateTime.now(),
    val modifiedDate: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User?,

    @OneToOne(mappedBy = "card", cascade = [CascadeType.ALL])
    val cardLink: CardLinkEntity? = null,

    @OneToMany(mappedBy = "card", cascade = [CascadeType.ALL])
    val socialNetworks: List<SocialNetwork>? = emptyList(),

    @OneToOne(mappedBy = "card", orphanRemoval = true, cascade = [CascadeType.ALL])
    val companyInfoEntity: CompanyInfoEntity? = null
)

fun CardDto.toEntity() =
    CardEntity(
        cardName = this.cardName,
        fullName = this.fullName,
        surname = this.surname,
        position = this.position,
        phoneNumber = this.phoneNumber,
        email = this.email,
        degree = this.degree,
        aboutMe = this.aboutMe,
        address = this.address,
        user = null,
        companyInfoEntity = null
    )
