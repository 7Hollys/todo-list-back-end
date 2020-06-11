package com.hollys.todoList.social

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class LoginRequest {

    @NotBlank
    @Email
    private val email: String? = null

//    @NotBlank
//    private val password: String? = null

    // Getters and Setters (Omitted for brevity)
}