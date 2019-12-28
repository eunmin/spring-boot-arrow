package com.eunmin.arrow.service

import arrow.core.Either
import arrow.core.flatMap
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
    fun get(id: String): Either<UserException, UserPayload> =
        userRepository.get(id).flatMap { userDocument ->
            userDocument.toDomain().map { user ->
                UserPayload.fromDomain(user)
            }
        }

    fun create(input: CreateUserInput): Either<UserException, UserPayload> =
        input.toDomain().flatMap { user ->
            val userDocument = UserDocument.fromDomain(user)
            userRepository.create(userDocument).flatMap { createdUserDocument ->
                createdUserDocument.toDomain().map { createdUser ->
                    UserPayload.fromDomain(createdUser)
                }
            }
        }
}