package com.hollys.todoList.entity

import com.hollys.todoList.domain.model.AuthProvider
import com.hollys.todoList.domain.model.UserModel
import lombok.Data
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "USER")
@Data
data class User(
        @Id val id: Long? = null,
        val name: String,
        val email: String,
        val profileImage: String,
        val emailVerified: Boolean = false,
        val accountLocked: Boolean = false,
        val provider: AuthProvider,
        val providerId: String,
        val roles: String,
        val createdAt: Date,
        val updatedAt: Date

) {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private val id: Long? = null
//
//    @Email
//    @Column(name = "EMAIL", nullable = false)
//    private val email: String? = null
//
//    @Column(name = "EMAIL_VERIFIED")
//    private val emailVerified: String? = null
//
//    @Column(name = "ACCOUNT_LOCKED")
//    private val accountLocked: String? = null
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    @Column(name = "PROVIDER")
//    private val provider: String? = null
//
//    @Column(name = "PROVIDER_ID")
//    private val providerId: String? = null
//
//    @Column(name = "name", nullable = false)
//    private val name: String? = null
//
//    @Column(name = "TYPE")
//    private val type: Long? = null
//
//    @Column(name = "PROFILE_IMAGE")
//    private val profileImage: String? = null
//
//    @Column(name = "roles")
//    private val roles: String? = null
//
//    @Column(name = "CREATE_AT")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private val createAt: Instant? = null
//
//    @Column(name = "UPDATE_AT")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private val updateAt: Instant? = null
//
//    @Column(name = "DROP_AT")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private val dropAt: Instant? = null


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
            updatedAt = user.updateAt,
            profileImage = user.profileImage
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
            updateAt = user.updatedAt,
            profileImage = user.profileImage
    )
}
