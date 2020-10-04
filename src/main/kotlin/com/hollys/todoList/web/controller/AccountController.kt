package com.hollys.todoList.web.controller

import com.hollys.todoList.auth.misc.TokenProvider
import com.hollys.todoList.auth.util.UserPrincipal
import com.hollys.todoList.domain.model.AuthProvider
import com.hollys.todoList.domain.model.UserModel
import com.hollys.todoList.domain.service.UserService
import com.hollys.todoList.util.BadRequestException
import com.hollys.todoList.web.dto.AuthResponse
import com.hollys.todoList.web.dto.LoginRequest
import com.hollys.todoList.web.dto.SignUpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping(AccountController.AUTH_BASE_URI, consumes = ["application/json"])
//@SafeHtml.Tag(name = "Account Api", description = " This contains url related to user account")
class AccountController(
        @Autowired private val authenticationManager: AuthenticationManager,
        @Autowired private val userService: UserService,
        @Autowired private val passwordEncoder: PasswordEncoder,
        @Autowired private val tokenProvider: TokenProvider
) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<AuthResponse>? {
        val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.email,
                        loginRequest.password
                )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = tokenProvider.createToken(authentication)
        val userPrincipal = authentication.principal as UserPrincipal

        return ResponseEntity.ok(AuthResponse(accessToken = token, name = userPrincipal.mName, email = userPrincipal.mEmail, imageUrl = userPrincipal.profileImage, id = userPrincipal.id))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Boolean>? {
        if (userService.existsByEmail(signUpRequest.email)) {
            throw BadRequestException("Email address already in use.")
        }
        // Creating user's account
        val user = UserModel(
                name = signUpRequest.name,
                email = signUpRequest.email,
//                password = passwordEncoder.encode(signUpRequest.password),
                profileImage = "",
                roles = "USER_ROLE",
                providerId = "",
                provider = AuthProvider.local,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                uuid = UUID.randomUUID()
        )
        val result = userService.saveUser(user)
//        return ResponseEntity.ok(ApiResponse(true, "User registered successfully"))
        return ResponseEntity.ok(true)

    }


    companion object {
        const val AUTH_BASE_URI = "/api/account"
    }
}