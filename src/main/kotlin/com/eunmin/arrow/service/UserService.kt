package com.eunmin.arrow.service

import arrow.core.Either
import arrow.core.extensions.fx
import arrow.core.flatMap
import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.monad.monad
import arrow.fx.fix
import arrow.mtl.EitherT
import arrow.mtl.extensions.eithert.monad.monad
import arrow.mtl.value
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
    fun get(id: String): IO<Either<UserException, UserPayload>> =
            EitherT.monad<ForIO, UserException>(IO.monad()).fx.monad {
                val userDocument = EitherT(userRepository.get(id)).bind()
                val user = EitherT(IO.just(userDocument.toDomain())).bind()
                UserPayload.fromDomain(user)
            }.value().fix()


    fun create(input: CreateUserInput): IO<Either<UserException, UserPayload>> =
            EitherT.monad<ForIO, UserException>(IO.monad()).fx.monad {
                val user = EitherT(IO.just(input.toDomain())).bind()
                val userDocument = UserDocument.fromDomain(user)
                val createdUserDocument = EitherT(userRepository.create(userDocument)).bind()
                val createdUser = EitherT(IO.just(createdUserDocument.toDomain())).bind()
                UserPayload.fromDomain(createdUser)
            }.value().fix()
}