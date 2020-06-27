package com.hollys.todoList.auth.util

import com.hollys.todoList.domain.model.UserModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class UserPrincipal(val id: Long, val mEmail: String,  val mAuthorities: Collection<GrantedAuthority?>,
                    val mAttributes: Map<String, Any>, val mName: String, val profileImage: String, val uuid :UUID) : OAuth2User, UserDetails {

    companion object {
        fun create(userModel: UserModel, attributes: Map<String, Any>, authorities: Collection<GrantedAuthority?>): UserPrincipal {
            return UserPrincipal(id = userModel.id!!, mEmail = userModel.email,
                    mAttributes = attributes, mAuthorities = authorities, mName = userModel.name, profileImage = userModel.profileImage, uuid = userModel.uuid )
        }

    }


    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return mEmail
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        TODO("Not yet implemented")
    }


    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return mAuthorities
    }

    override fun getName(): String {
        return mName
    }

    override fun getAttributes(): Map<String, Any> {
        return mAttributes
    }
}