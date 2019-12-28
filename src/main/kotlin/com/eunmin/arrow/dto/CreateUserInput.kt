package com.eunmin.arrow.dto

import com.eunmin.arrow.domain.String16
import com.eunmin.arrow.domain.String80
import com.eunmin.arrow.domain.User

data class CreateUserInput(
        val id: String,
        val name: String
)

fun CreateUserInput.toDomain(): User = User(String16(this.id), String80(this.name))