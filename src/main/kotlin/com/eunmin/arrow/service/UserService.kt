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
    fun get(id: String): UserPayload {
        val userDocument = userRepository.get(id)
        val user = userDocument.toDomain()
        return UserPayload.fromDomain(user)
    }

    fun create(input: CreateUserInput): UserPayload {
        val user = input.toDomain()
        val userDocument = UserDocument.fromDomain(user)
        val createdUserDocument = userRepository.create(userDocument)
        val createdUser = createdUserDocument.toDomain()
        return UserPayload.fromDomain(createdUser)
    }
}