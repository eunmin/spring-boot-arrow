package com.eunmin.arrow.repository

import arrow.core.Either
import com.eunmin.arrow.domain.UserException
import com.eunmin.arrow.dto.UserDocument

interface UserRepository {
    fun create(userDocument: UserDocument): Either<UserException, UserDocument>
    fun get(id: String): Either<UserException, UserDocument>
}