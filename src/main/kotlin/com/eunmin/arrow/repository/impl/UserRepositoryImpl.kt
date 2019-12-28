package com.eunmin.arrow.repository.impl

import com.eunmin.arrow.domain.DuplicateUser
import com.eunmin.arrow.domain.UserNotFound
import com.eunmin.arrow.dto.UserDocument
import com.eunmin.arrow.repository.UserRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
        private val mongoTemplate: MongoTemplate
): UserRepository {
    override fun create(userDocument: UserDocument): UserDocument =
            try {
                mongoTemplate.insert(userDocument)
            } catch (e: DuplicateKeyException) {
                throw DuplicateUser(userDocument.id)
            }

    override fun get(id: String): UserDocument =
            mongoTemplate.findById(id, UserDocument::class.java) ?: throw UserNotFound(id)
}