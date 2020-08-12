package com.hollys.todoList.domain.model

import java.time.LocalDateTime
import java.util.*

data class UserModel(val id: Long? = null,
                     val name: String,
                     val email: String,
                     val profileImage: String,
                     val emailVerified: Boolean = false,
                     val accountLocked: Boolean = false,
                     val provider: AuthProvider,
                     val providerId: String,
                     val roles: String,
                     val createdAt: LocalDateTime,
                     val updatedAt: LocalDateTime,
//                     val dropAt: LocalDateTime,
                     val uuid: UUID)