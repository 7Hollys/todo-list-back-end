package com.hollys.todoList.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.hollys.todoList.auth.misc.TokenProvider
import com.hollys.todoList.domain.model.AuthProvider
import com.hollys.todoList.domain.model.UserModel
import com.hollys.todoList.domain.service.UserService
import com.hollys.todoList.entity.User
import com.hollys.todoList.entity.UserEntityMapper
import com.hollys.todoList.repo.UserRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class LoginService(
        @Autowired private val queryFactory: JPAQueryFactory,
        @Autowired private val userRepository: UserRepository,
        @Autowired private val authenticationManager: AuthenticationManager,
        @Autowired private val tokenProvider: TokenProvider,
        @Autowired val userService: UserService
) {
    fun login(idToken: String): String {
        val firebaseToken: FirebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val userStatus = userRepository.findByEmail(firebaseToken.email)

        var user: User? = null
        if (!userStatus.isPresent) {
            user = signup(firebaseToken)
        } else {
            user = userStatusUpdate(firebaseToken, userStatus)
        }

        val userDetails: UserDetails = userService.getUserById(user.id.toString())!!
        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        val token = tokenProvider.createToken(authentication)
        return token;
    }

    fun signup(firebaseToken: FirebaseToken): User {
        val firebase = firebaseToken.claims.get("firebase").toString()
                .split(",", "{", "}", "=", "[", "]").map { it.trim() }
        var provider: AuthProvider = AuthProvider.google
        try {
            provider = AuthProvider.valueOf(firebase[firebase.indexOf("sign_in_provider") + 1])
        } catch (ex: Exception) {
            provider = AuthProvider.google
        }
        val providerId = firebase[firebase.indexOf("identities") + 4]
        val user = UserModel(
                name = firebaseToken.name,
                email = firebaseToken.email,
                emailVerified = firebaseToken.isEmailVerified,
                profileImage = firebaseToken.picture,
                roles = "USER_ROLE",
                providerId = providerId,
                provider = provider,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                uuid = UUID.randomUUID()
        )
        return userRepository.save(UserEntityMapper.from(user))
    }

    fun userStatusUpdate(firebaseToken: FirebaseToken, userStatus: Optional<User>): User {
        val user = userStatus.get()
        val userModified = User(
                id = user.id,
                name = firebaseToken.name,
                email = user.email,
                emailVerified = firebaseToken.isEmailVerified,
                accountLocked = user.accountLocked,
                profileImage = firebaseToken.picture,
                provider = user.provider,
                providerId = user.providerId,
                roles = user.roles,
                createdAt = user.createdAt,
                updatedAt = LocalDateTime.now(),
                uuid = user.uuid
        )
        return userRepository.save(userModified)
    }

}