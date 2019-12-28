package com.eunmin.arrow.repository.impl

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import arrow.fx.IO
import com.eunmin.arrow.domain.DuplicateUser
import com.eunmin.arrow.domain.UserException
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
    override fun create(userDocument: UserDocument): IO<Either<UserException, UserDocument>> = IO {
        try {
            Right(mongoTemplate.insert(userDocument))
        } catch (e: DuplicateKeyException) {
            Left(DuplicateUser(userDocument.id))
        }
    }

    override fun get(id: String): IO<Either<UserException, UserDocument>> = IO {
        mongoTemplate.findById(id, UserDocument::class.java)?.let { Right(it) } ?: Left(UserNotFound(id))
    }
}