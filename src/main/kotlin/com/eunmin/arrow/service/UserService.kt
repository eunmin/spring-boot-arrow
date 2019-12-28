package com.eunmin.arrow.service

import arrow.core.Either
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
    fun get(id: String): Either<UserException, UserPayload> {
        return userRepository.get(id).map {
            it.toDomain()
        }.map {
            UserPayload.fromDomain(it)
        }
    }

    fun create(input: CreateUserInput): Either<UserException, UserPayload> {
        val user = input.toDomain()
        val userDocument = UserDocument.fromDomain(user)
        return userRepository.create(userDocument).map {
            it.toDomain()
        }.map {
            UserPayload.fromDomain(it)
        }
    }
}