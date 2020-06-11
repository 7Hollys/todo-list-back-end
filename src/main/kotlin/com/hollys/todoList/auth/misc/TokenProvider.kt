package com.hollys.todoList.auth.misc

import com.hollys.todoList.config.AppProperties
import com.hollys.todoList.auth.util.UserPrincipal
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenProvider(private val appProperties: AppProperties) {

    fun createToken(authentication: Authentication): String {
        //Authentication 안에 토큰과 userinfo담김
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal
        return generateToken(userPrincipal)
    }

    fun generateToken(userPrincipal: UserPrincipal): String {
        val now = Date()
        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)
        return Jwts.builder()
                .setSubject(userPrincipal.id.toString())
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.auth.tokenSecret)
                .compact()
    }

    fun getUserIdFromToken(token: String?): String {
        return Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(token).body.subject
    }

    fun validateToken(authToken: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(authToken)
            true
        } catch (ex: Exception) {
            false
        }
    }


}