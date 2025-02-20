package ru.musailov.cards.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {
    private val jwtSecret = "LALALA228SLSNIYDAUNPAPAPAPASKDKEW2281337012SPQLSQSA"
    private val jwtExpirationMs = 10_800_000 // 3 часа

    fun generateToken(email: String): String {
        return Jwts.builder()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigninKey())
            .compact()
    }


    private fun getSigninKey(): SecretKey {
        val keyBytes = Decoders.BASE64URL.decode(jwtSecret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun getEmailFromToken(token: String): String? {
        return try {
            val payload = Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parse(token)
                .payload.toString()
            payload.substring(payload.indexOf("sub=") + 4, payload.indexOf(", iat="))
        } catch (e: Exception) {
            throw e
        }
    }


}
