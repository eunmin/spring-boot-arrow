package com.eunmin.arrow.dto

import arrow.core.Either
import arrow.core.flatMap
import com.eunmin.arrow.domain.Userid
import com.eunmin.arrow.domain.Username
import com.eunmin.arrow.domain.User
import com.eunmin.arrow.domain.UserException

data class CreateUserInput(
        val id: String,
        val name: String
)

fun CreateUserInput.toDomain(): Either<UserException, User> =
    Userid(this.id).flatMap { id ->
        Username(this.name).map { name ->
            User(id, name)
        }
    }
