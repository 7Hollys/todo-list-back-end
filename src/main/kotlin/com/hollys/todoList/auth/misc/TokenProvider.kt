package com.hollys.todoList.auth.misc

import com.hollys.todoList.auth.util.OAuth2UserInfo
import com.hollys.todoList.config.AppProperties
import com.hollys.todoList.auth.util.UserPrincipal
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenProvider(private val appProperties: AppProperties) {

    fun createToken(authentication: Authentication): String {
        //Authentication 안에 토큰과 userinfo담김
        val ip: String = (authentication.details as WebAuthenticationDetails).remoteAddress // 접속 ip
        val userInfo: Map<String, Any> = (authentication.principal as DefaultOidcUser).attributes
//        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal
        return generateToken(userInfo)
    }


    fun generateToken(userInfo: Map<String, Any>): String {
        val now = Date()
        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)
        return Jwts.builder()
                .setSubject(listOf("email" to userInfo.get("email"), "name" to userInfo.get("name"), "picture" to userInfo.get("picture"), "locale" to userInfo.get("locale")).toString())
//        , userInfo.get("uuid") as String
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


//    fun generateToken(userPrincipal: UserPrincipal): String {
//        val now = Date()
//        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)
//        return Jwts.builder()
//                .setSubject(userPrincipal.id.toString())
//                .setIssuedAt(Date())
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, appProperties.auth.tokenSecret)
//                .compact()
//    }
//
//    fun getUserIdFromToken(token: String?): String {
//        return Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(token).body.subject
//    }
//
//    fun validateToken(authToken: String?): Boolean {
//        return try {
//            Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(authToken)
//            true
//        } catch (ex: Exception) {
//            false
//        }
//    }


}