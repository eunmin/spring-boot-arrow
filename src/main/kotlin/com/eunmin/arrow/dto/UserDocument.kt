package com.eunmin.arrow.dto

import com.eunmin.arrow.domain.String16
import com.eunmin.arrow.domain.String80
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
                fun fromDomain(user: User) = UserDocument(user.id.value, user.name.value)
        }
}

fun UserDocument.toDomain(): User = User(String16(this.id), String80(this.name))