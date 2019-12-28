package com.eunmin.arrow.service

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
    fun get(id: String): UserPayload =
            UserPayload.fromDomain(userRepository.get(id).toDomain())

    fun create(input: CreateUserInput): UserPayload =
            UserPayload.fromDomain(userRepository.create(UserDocument.fromDomain(input.toDomain())).toDomain())
}