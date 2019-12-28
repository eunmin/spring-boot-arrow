package com.eunmin.arrow.domain

data class User(
        val id: String16,
        val name: String80
)

class DuplicatedUserException(message: String) : ErrorCodeException(-1405, message)
class UserNotFoundException(message: String) : ErrorCodeException(-1404, message)
class LimitExceededException(message: String): ErrorCodeException(-1401, message)