package ru.musailov.cards.dto

import ru.musailov.cards.model.CardEntity
import ru.musailov.cards.model.SocialNetwork

data class SocialNetworkDto(
    val name: String?,
    val link: String?
)

val TELEGRAM = setOf("TELEGRAM", "ТЕЛЕГА", "ТЕЛЕГРАМ", "ТГ", "TG")
val TIKTOK = setOf("TIKTOK", "ТИКТОК", "TT", "ТТ")
val WHATSAPP = setOf("WHATSAPP", "ВАТСАП")
val YOUTUBE = setOf("ЮТУБ", "ЮТЮБ", "YOUTUBE", "YT")

fun SocialNetwork.toDto() =
    SocialNetworkDto(
        this.name,
        this.link
    )

fun SocialNetworkDto.toEntity(card: CardEntity) =
    SocialNetwork(
        name = this.name,
        link = enrichLink(this.name!!.uppercase(), this.link!!),
        card = card
    )

fun enrichLink(name: String, link: String): String {
    return if (name in TELEGRAM && link.startsWith("@"))
        "https://t.me/${link.substring(1)}"
    else if (name in TIKTOK && link.startsWith("@"))
        "https://tiktok.com/$link"
    else if (name in WHATSAPP)
        "https://wa.me/$link"
    else if (name in YOUTUBE && link.startsWith("@"))
        "https://youtube.com/$link"
    else link
}