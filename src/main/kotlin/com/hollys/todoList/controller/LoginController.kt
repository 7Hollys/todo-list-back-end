//package com.hollys.todoList.controller
//
//import com.hollys.todoList.auth.OAuth2UserInfo
//import com.hollys.todoList.auth.OAuth2UserInfoFactory
//import jdk.nashorn.internal.runtime.regexp.joni.Config.log
//import org.springframework.core.env.Environment
//import org.springframework.http.HttpHeaders
//import org.springframework.http.MediaType
//
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
//import org.springframework.stereotype.Controller
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.ResponseBody
//import javax.servlet.http.HttpSession
//
//@Controller
//class LoginController(
//        val environment: Environment
//) {
//
//
//    // index페이지 호출 시 로그인 페이지로 이동
//    @GetMapping("/")
//    fun index() = "redirect:/login"
//
//    // 로그인 페이지
//    @GetMapping("/login")
//    fun login() = "login"
//
//    // 로그인 성공 URI
//    @GetMapping("/login/complelte")
//    fun loginComplete(data: SecurityContextHolder, httpSession: HttpSession, oAuth2UserRequest: OAuth2UserRequest
//    ): String {
//
////        daftest :OAuth2UserRequest
//
//
//        val clientId = environment.getProperty("rcheung.oauth2.client.id") ?: ""
//        val secret = environment.getProperty("rcheung.oauth2.client.secret") ?: ""
//        val tokenUrl = environment.getProperty("security.oauth2.client.access-token-uri") ?: ""
//
//        val client: OAuth2AuthorizedClient
//
//
//        val headers = HttpHeaders()
//        headers.setBasicAuth(clientId, secret)
//        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
//
////        var httpData = http.authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .oauth2Login();
//
//        log.print("넘어온값 , $httpSession");
////        return "redirect:/welcome";
//        return "google_login";
//
//    }
//
//    // 로그인 후 웰컴 페이지 (인증 후 접근 가능)
//    @GetMapping("/welcome")
//    @ResponseBody
//    fun welcome() = "Hello! Social Login!!"
//
//    @GetMapping("test")
//    fun selectLastYearAmount(): String {
//        return "Test !!!";
//    }
//
////    @PostMapping("/login")
////    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest?): ResponseEntity<*>? {
////        val authentication: Authentication = authenticationManager.authenticate(
////                UsernamePasswordAuthenticationToken(
////                        loginRequest.getEmail(),
////                        loginRequest.getPassword()
////                )
////        )
////        SecurityContextHolder.getContext().authentication = authentication
////        val token: String = tokenProvider.createToken(authentication)
////        return ResponseEntity.ok<Any>(AuthResponse(token))
////    }
//
//
//}