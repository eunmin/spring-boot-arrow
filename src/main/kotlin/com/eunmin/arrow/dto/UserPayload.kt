package com.eunmin.arrow.dto

import com.eunmin.arrow.domain.User

data class UserPayload(
        val id: String,
        val name: String
) {
    companion object {
        fun fromDomain(user: User): UserPayload = UserPayload(user.id.value, user.name.value)
    }
}