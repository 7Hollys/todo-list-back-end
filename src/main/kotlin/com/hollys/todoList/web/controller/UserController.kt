package com.hollys.todoList.web.controller

import com.hollys.todoList.auth.util.UserPrincipal
import com.hollys.todoList.web.controller.UserController.Companion.USER_BASE_URI
import com.hollys.todoList.web.dto.UserProfileResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(USER_BASE_URI)
class UserController {

    companion object {
        const val USER_BASE_URI = "/api/users"
    }

    @GetMapping("/me")
    fun getMyProfile(): ResponseEntity<UserProfileResponse> {
        val user = SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        return ResponseEntity.ok(UserProfileResponse(id = user.id, name = user.name, email = user.mEmail, profileImage = user.profileImage))
    }

}