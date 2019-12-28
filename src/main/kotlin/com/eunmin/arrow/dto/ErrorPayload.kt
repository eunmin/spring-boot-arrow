package com.eunmin.arrow.dto

import com.eunmin.arrow.domain.ErrorCodeException

data class ErrorPayload(
        val code: Int,
        val message: String
) {
    companion object {
        fun fromDomain(e: ErrorCodeException): ErrorPayload = ErrorPayload(e.code, e.message)
    }
}