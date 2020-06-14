package com.hollys.todoList.web.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginRequest(
        @NotBlank @Email val email: String,
        @NotBlank val password: String)