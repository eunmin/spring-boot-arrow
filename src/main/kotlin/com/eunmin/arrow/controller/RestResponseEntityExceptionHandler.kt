package com.eunmin.arrow.controller

import com.eunmin.arrow.domain.*
import com.eunmin.arrow.dto.ErrorPayload
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(DuplicateUser::class)
    fun handleDuplicatedUserException(e: UserException, request: WebRequest)
            = handleExceptionInternal(e, ErrorPayload.fromDomain(e), HttpHeaders(), HttpStatus.CONFLICT, request)

    @ExceptionHandler(UserNotFound::class)
    fun handleUserNotFoundException(e: UserException, request: WebRequest)
            = handleExceptionInternal(e, ErrorPayload.fromDomain(e), HttpHeaders(), HttpStatus.NOT_FOUND, request)

    @ExceptionHandler(UseridLimitExceeded::class, UsernameLimitExceeded::class)
    fun handleLimitExceededException(e: UserException, request: WebRequest)
            = handleExceptionInternal(e, ErrorPayload.fromDomain(e), HttpHeaders(), HttpStatus.BAD_REQUEST, request)

}