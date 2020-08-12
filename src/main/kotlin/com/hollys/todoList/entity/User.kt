package com.hollys.todoList.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.hollys.todoList.domain.model.AuthProvider
import com.hollys.todoList.domain.model.UserModel
import lombok.AccessLevel
import lombok.NoArgsConstructor
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long?,

        @Column(name = "name")
        val name: String,

        @Email
        @Column(name = "email", nullable = false)
        val email: String,

        @Column(name = "email_verified")
        val emailVerified: Boolean,

        @Column(name = "account_locked")
        val accountLocked: Boolean,

        @Column(name = "profile_image")
        val profileImage: String,

        @NotNull
        @Enumerated(EnumType.STRING)
        @Column(name = "provider")
        val provider: AuthProvider,

        @Column(name = "provider_id")
        val providerId: String,

        @Column(name = "roles")
        val roles: String,

        @Column(name = "created_at")
        val createdAt: LocalDateTime,

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime,

//        @Column(name = "drop_at")
//        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//        val dropAt: LocalDateTime,

        @Column(name = "uuid")
        val uuid: UUID
) {

}

object UserEntityMapper {
    fun from(user: UserModel): User = User(
            id = user.id,
            email = user.email,
            name = user.name,
            emailVerified = user.emailVerified,
            accountLocked = user.accountLocked,
            provider = user.provider,
            providerId = user.providerId,
            roles = user.roles,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            profileImage = user.profileImage,
//            dropAt = user.dropAt,
            uuid = user.uuid
    )

    fun to(user: User): UserModel = UserModel(
            id = user.id,
            email = user.email,
            name = user.name,
            emailVerified = user.emailVerified,
            accountLocked = user.accountLocked,
            provider = user.provider,
            providerId = user.providerId,
            roles = user.roles,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            profileImage = user.profileImage,
//            dropAt = user.dropAt,
            uuid = user.uuid
    )
}
