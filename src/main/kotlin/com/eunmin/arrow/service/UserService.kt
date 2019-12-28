package com.eunmin.arrow.service

import arrow.core.Either
import arrow.core.extensions.fx
import com.eunmin.arrow.domain.UserException
import com.eunmin.arrow.dto.CreateUserInput
import com.eunmin.arrow.dto.UserDocument
import com.eunmin.arrow.dto.UserPayload
import com.eunmin.arrow.dto.toDomain
import com.eunmin.arrow.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun get(id: String): Either<UserException, UserPayload> = Either.fx {
        val (userDocument) = userRepository.get(id)
        val (user) = userDocument.toDomain()
        UserPayload.fromDomain(user)
    }

    fun create(input: CreateUserInput): Either<UserException, UserPayload> = Either.fx {
        val (user) = input.toDomain()
        val userDocument = UserDocument.fromDomain(user)
        val (createdUserDocument) = userRepository.create(userDocument)
        val (createdUser) = createdUserDocument.toDomain()
        UserPayload.fromDomain(createdUser)
    }
}