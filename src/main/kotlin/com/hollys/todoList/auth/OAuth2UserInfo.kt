package com.hollys.todoList.auth

import lombok.Getter
import lombok.Setter


abstract class OAuth2UserInfo {

    //    @Setter
    @Getter
    var attributes: Map<String, Any>? = null

    open fun OAuth2UserInfo(attributes: Map<String, Any>) {
        this.attributes = attributes
    }

//     open fun getAttributes(): Map<String, Any>? {
//         return this.attributes
//    }


    abstract fun getId(): String?

    abstract fun getName(): String?

    abstract fun getEmail(): String?

    abstract fun getImageUrl(): String?
}