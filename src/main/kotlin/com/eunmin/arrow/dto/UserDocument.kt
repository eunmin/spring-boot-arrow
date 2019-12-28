package com.eunmin.arrow.dto

import arrow.core.Either
import arrow.core.extensions.fx
import arrow.core.flatMap
import com.eunmin.arrow.domain.Userid
import com.eunmin.arrow.domain.Username
import com.eunmin.arrow.domain.User
import com.eunmin.arrow.domain.UserException
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("user")
data class UserDocument(
        @Id
        val id: String,

        @Field
        val name: String
) {
        companion object {
                fun fromDomain(user: User): UserDocument = UserDocument(user.id.value, user.name.value)
        }
}

fun UserDocument.toDomain(): Either<UserException, User> = Either.fx {
    val (id) = Userid(id)
    val (name) = Username(name)
    User(id, name)
}