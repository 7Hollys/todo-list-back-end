package com.hollys.todoList.web.dto

data class AuthResponse(
        val accessToken: String,
        val tokenType: String = "Bearer ",
        val name: String,
        val email: String,
        val imageUrl: String,
        val id: Long
)