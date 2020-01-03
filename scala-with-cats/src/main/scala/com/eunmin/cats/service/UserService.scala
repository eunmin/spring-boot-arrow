package com.eunmin.cats.service

import cats.data.EitherT
import cats.effect.IO
import com.eunmin.cats.domain.UserException
import com.eunmin.cats.dto.{CreateUserInput, UserDocument, UserPayload}
import com.eunmin.cats.repository.UserRepository

class UserService(userRepository: UserRepository) {
  def get(id: String): IO[Either[UserException, UserPayload]] = (for {
    userDocument <- EitherT( userRepository.get(id) )
    user         <- EitherT( IO.pure(userDocument.toDomain()) )
  } yield UserPayload.fromDomain(user)).value

  def create(input: CreateUserInput): IO[Either[UserException, UserPayload]] = (for{
    user                <- EitherT( IO.pure(input.toDomain()) )
    userDocument        =  UserDocument.fromDomain(user)
    createdUserDocument <- EitherT( userRepository.create(userDocument) )
    createdUser         <- EitherT( IO.pure(createdUserDocument.toDomain()) )
  } yield UserPayload.fromDomain(createdUser)).value
}
