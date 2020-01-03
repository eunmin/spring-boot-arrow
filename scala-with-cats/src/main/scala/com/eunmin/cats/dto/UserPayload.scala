package com.eunmin.cats.dto

import com.eunmin.cats.domain.User

case class UserPayload()

object UserPayload {
  def fromDomain(user: User): UserPayload = UserPayload()
}
