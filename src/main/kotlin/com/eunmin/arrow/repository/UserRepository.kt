package com.eunmin.arrow.repository

import com.eunmin.arrow.dto.UserDocument

interface UserRepository {
    fun create(userDocument: UserDocument): UserDocument
    fun get(id: String): UserDocument
}