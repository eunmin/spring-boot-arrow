package com.eunmin.arrow.domain

data class String16 private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): String16 {
            if (value.length > 16) throw LimitExceededException("$value is too long (maximum is 16)")
            return String16(value)
        }
    }
}

data class String80 private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): String80 {
            if (value.length > 80) throw LimitExceededException("$value is too long (maximum is 16)")
            return String80(value)
        }
    }
}

abstract class ErrorCodeException(val code:Int, override val message: String): RuntimeException()