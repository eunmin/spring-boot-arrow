package com.eunmin.arrow.repository

import arrow.core.Either
import arrow.fx.IO
import com.eunmin.arrow.domain.UserException
import com.eunmin.arrow.dto.UserDocument

interface UserRepository {
    fun create(userDocument: UserDocument): IO<Either<UserException, UserDocument>>
    fun get(id: String): IO<Either<UserException, UserDocument>>
}