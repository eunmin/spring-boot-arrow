package com.eunmin.cats.dto

import cats.syntax.either._
import com.eunmin.cats.domain.{User, UserException}

case class CreateUserInput() {
  def toDomain(): Either[UserException, User] = Either.right(User())
}
