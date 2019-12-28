package com.eunmin.arrow.domain

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right

data class Userid private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): Either<UserException, Userid> =
            if (value.length > 16) {
                Left(UseridLimitExceeded(value))
            } else {
                Right(Userid(value))
            }
    }
}

data class Username private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): Either<UserException, Username> =
            if (value.length > 80) {
                Left(UsernameLimitExceeded(value))
            } else {
                Right(Username(value))
            }
    }
}

data class User(
        val id: Userid,
        val name: Username
)

sealed class UserException(val code:Int, override val message: String): RuntimeException() {
    data class UseridLimitExceeded(val id: String) : UserException(-1401, "id $id is too long (maximum is 16)")
    data class UsernameLimitExceeded(val name: String) : UserException(-1402, "name $name is too long (maximum is 18)")
    data class NotFound(val id: String) : UserException(-1404, "userid $id is not found")
    data class Duplicate(val id: String) : UserException(-1405, "userid $id is already exists")
}

typealias UseridLimitExceeded = UserException.UseridLimitExceeded
typealias UsernameLimitExceeded = UserException.UsernameLimitExceeded
typealias UserNotFound = UserException.NotFound
typealias DuplicateUser = UserException.Duplicate