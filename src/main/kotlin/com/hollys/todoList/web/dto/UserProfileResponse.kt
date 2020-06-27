package com.hollys.todoList.web.dto

data class UserProfileResponse(
        val id: Long,
        val name: String,
        val email: String,
        val profileImage: String
)