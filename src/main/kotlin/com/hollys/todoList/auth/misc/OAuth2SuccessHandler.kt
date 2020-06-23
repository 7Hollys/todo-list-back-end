package com.hollys.todoList.auth.misc

import com.hollys.todoList.auth.misc.OAuth2RequestRepository.Companion.REDIRECT_URL_PARAM_COOKIE_NAME
import com.hollys.todoList.config.AppProperties
import com.hollys.todoList.util.BadRequestException
import com.hollys.todoList.util.CookieUtils.getCookie
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import java.net.URI
import java.util.*
import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2SuccessHandler(@Autowired val tokenProvider: TokenProvider,
                           @Autowired private val appProperties: AppProperties,
                           @Autowired val oAuth2RequestRepository: OAuth2RequestRepository)

    : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            return
        }
        clearAuthenticationAttributes(request, response)
//        createdCookie(request, response, authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication): String {
//authentication 안에 userinfo 담김
        val redirectUrl: String = getCookie(request!!, REDIRECT_URL_PARAM_COOKIE_NAME)?.let { return@let it.value } ?: ""

        if (redirectUrl.isEmpty() && !isAuthorizedRedirectUrl(redirectUrl)) {
            throw BadRequestException("Sorry! We've got an Unauthorized Redirect URL and can't proceed with the authentication")
        }
//  redirectURL = http://localhost:4200/auth/token?token=  createToken
        return UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("token", tokenProvider.createToken(authentication))
                .build().toUriString()
    }

    // 인증 속성 지움
    private fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        // 승인 요청 쿠키 지움
        oAuth2RequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    private fun isAuthorizedRedirectUrl(uri: String): Boolean {

        val clientRedirectUri = URI.create(uri)
        appProperties.oauth2.authorizedRedirectUrls.map {
            val authorizedURL = URI.create(it)
            if (authorizedURL.host.toUpperCase() == clientRedirectUri.host.toUpperCase()) {
                return true
            }
        }
        return false
    }

//    private fun createdCookie(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
//        val cookie = Cookie("JWT", authentication.principal as String?)
//        cookie.isHttpOnly = true
//        cookie.maxAge = appProperties.auth.tokenExpirationMsec.toInt()
//        response.addCookie(cookie)
//    }

}

@Component
class OAuth2FailureHandler(@Autowired val oAuth2RequestRepository: OAuth2RequestRepository)
    : SimpleUrlAuthenticationFailureHandler() {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        var targetUrl: String = getCookie(request, REDIRECT_URL_PARAM_COOKIE_NAME)?.value ?: "/"
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.localizedMessage)
                .build().toUriString()
        oAuth2RequestRepository.removeAuthorizationRequestCookies(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}