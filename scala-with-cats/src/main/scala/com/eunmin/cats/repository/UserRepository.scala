package com.eunmin.cats.repository

import cats.effect.IO
import com.eunmin.cats.domain.UserException
import com.eunmin.cats.dto.UserDocument

trait UserRepository {
  def create(userDocument: UserDocument): IO[Either[UserException, UserDocument]]
  def get(id: String): IO[Either[UserException, UserDocument]]
}
