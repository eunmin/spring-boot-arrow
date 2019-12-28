package com.eunmin.arrow.dto

import com.eunmin.arrow.domain.Userid
import com.eunmin.arrow.domain.Username
import com.eunmin.arrow.domain.User

data class CreateUserInput(
        val id: String,
        val name: String
)

fun CreateUserInput.toDomain(): User = User(Userid(this.id), Username(this.name))