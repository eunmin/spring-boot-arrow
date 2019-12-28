package com.eunmin.arrow.dto

import com.eunmin.arrow.domain.Userid
import com.eunmin.arrow.domain.Username
import com.eunmin.arrow.domain.User
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

fun UserDocument.toDomain(): User = User(Userid(this.id), Username(this.name))