package com.hollys.todoList.config

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component
import java.util.*

@Component
@ConfigurationProperties(prefix = "app")
class AppProperties {
    val auth = Auth()

    data class Auth(var tokenSecret: String? = null, var tokenExpirationMsec: Long = 0)

    val oauth2 = OAuth2()

    data class OAuth2(var authorizedRedirectUrls: List<String> = ArrayList()){
        fun authorizedRedirectUrls(authorizedRedirectUrls: List<String>): OAuth2 {
            println(authorizedRedirectUrls.toString())
            this.authorizedRedirectUrls = authorizedRedirectUrls
            return this
        }
    }

//    private val auth = Auth()
//    private val oauth2 = OAuth2()
//
//    class Auth {
//        var tokenSecret: String? = null
//        var tokenExpirationMsec: Long = 0
//
//    }
//
//    class OAuth2 {
//        var authorizedRedirectUris: List<String> = ArrayList()
//            private set
//
//        fun authorizedRedirectUris(authorizedRedirectUris: List<String>): OAuth2 {
//            this.authorizedRedirectUris = authorizedRedirectUris
//            return this
//        }
//    }
//
//    fun getAuth(): Auth? {
//        return auth
//    }
//
//    fun getOauth2(): OAuth2? {
//        return oauth2
//    }

}