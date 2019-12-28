package com.eunmin.arrow.dto

import com.eunmin.arrow.domain.UserException

data class ErrorPayload(
        val code: Int,
        val message: String
) {
    companion object {
        fun fromDomain(e: UserException): ErrorPayload = ErrorPayload(e.code, e.message)
    }
}