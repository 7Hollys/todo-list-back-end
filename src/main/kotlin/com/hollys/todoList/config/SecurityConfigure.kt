package com.hollys.todoList.config

import com.hollys.todoList.auth.misc.*
import com.hollys.todoList.domain.service.CustomOAuth2UserService
import com.hollys.todoList.domain.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


/**
 * 스프링 시큐리티 관련 설정입니다.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
class SecurityConfigure(
        @Autowired val userService: UserService,
        @Autowired val tokenAuthenticationFilter: TokenAuthenticationFilter,
        @Autowired val customOAuth2UserService: CustomOAuth2UserService,
        @Autowired val oAuth2SuccessHandler: OAuth2SuccessHandler,
        @Autowired val oAuth2RequestRepository: OAuth2RequestRepository,
        @Autowired val oAuth2FailureHandler: OAuth2FailureHandler
): WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Throws(java.lang.Exception::class)
    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
                .userDetailsService<UserDetailsService>(userService)
                .passwordEncoder(passwordEncoder())
    }


    override fun configure(http: HttpSecurity) {
        http.cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(RestAuthenticationEntryPoint())
                .and().authorizeRequests()
                .antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/api/account/**", "/api/docs/**", "/api/docs.yaml", "/oauth2/**", "/login/oauth2/code/**").permitAll()
//                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login { oauth2Login ->
                    oauth2Login.authorizationEndpoint {
                        it.authorizationRequestRepository(oAuth2RequestRepository)
                    }
                    oauth2Login.userInfoEndpoint {
                        it.userService(customOAuth2UserService)
                    }
                    // 로그인 성공하면 이거탐
                    oauth2Login.successHandler(oAuth2SuccessHandler).failureHandler(oAuth2FailureHandler)
                }

//        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

    }












//    override fun configure(http: HttpSecurity) {
//
//        http
//                // URI 접근과 관련된 설정입니다.
//                .authorizeRequests()
//                .antMatchers("/", "/login/**", "/oauth2/**", "/images/**").permitAll()
//                .anyRequest().authenticated()
//
//                // OAuth2 적용 관련 설정입니다.
//                .and()
//                .oauth2Login()
//                .defaultSuccessUrl("/login/complelte")
//
//                // Iframe 사용 허용합니다.
//                .and()
//                .headers().frameOptions().disable()
//
//                // 인증되지 않은 사용자를 원하는 페이지로 이동시킵니다.
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
//
//                // 로그인 인증 후 이동 페이지 설정입니다.
//                .and()
//                .formLogin()
//                .successForwardUrl("/welcome")
//
//                // 로그아웃과 관련한 설정입니다.
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//
//                .and()
//                .addFilterAt(CharacterEncodingFilter(), CsrfFilter::class.java)
//                .csrf().disable()
//
//    }
//
//    /**
//     * OAuth2 설정입니다.
//     */
//    @Bean
//    fun clientRegistrationRepository(oAuth2ClientProperties: OAuth2ClientProperties,
//                                     customOAuth2ClientProperties: CustomOAuth2ClientProperties): InMemoryClientRegistrationRepository {
//
//        // 소셜 설정 등록
//        val registrations = oAuth2ClientProperties.registration.keys
//                .map { getRegistration(oAuth2ClientProperties, it) }
//                .filter { it != null }
//                .toMutableList()
//
//        // 추가 설정 프로퍼티
//        val customRegistrations = customOAuth2ClientProperties.registration
//
//        // 추가 소셜 설정을 기본 소셜 설정에 추가
//        for (customRegistration in customRegistrations) {
//
//            when (customRegistration.key) {
//                "kakao" -> registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
//                        .clientId(customRegistration.value.clientId)
//                        .clientSecret(customRegistration.value.clientSecret)
//                        .jwkSetUri(customRegistration.value.jwkSetUri)
//                        .build())
//                "naver" -> registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
//                        .clientId(customRegistration.value.clientId)
//                        .clientSecret(customRegistration.value.clientSecret)
//                        .jwkSetUri(customRegistration.value.jwkSetUri)
//                        .build())
//            }
//
//        }
//
//        return InMemoryClientRegistrationRepository(registrations)
//    }
//
//    // 공통 소셜 설정을 호출합니다.
//    private fun getRegistration(clientProperties: OAuth2ClientProperties, client: String): ClientRegistration? {
//        val registration = clientProperties.registration[client]
//        return when(client) {
//            "google" -> CommonOAuth2Provider.GOOGLE.getBuilder(client)
//                    .clientId(registration?.clientId)
//                    .clientSecret(registration?.clientSecret)
//                    .scope("email", "profile")
//                    .build()
////            페이스북을 추가하고 싶으면 SSL을 적용하셔야 합니다.
////            "facebook" -> CommonOAuth2Provider.FACEBOOK.getBuilder(client)
////                    .clientId(registration?.clientId)
////                    .clientSecret(registration?.clientSecret)
////                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
////                    .scope("email")
////                    .build()
//            else -> null
//        }
//    }







}