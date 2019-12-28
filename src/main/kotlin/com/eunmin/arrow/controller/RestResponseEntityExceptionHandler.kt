package com.eunmin.arrow.controller

import com.eunmin.arrow.domain.DuplicatedUserException
import com.eunmin.arrow.domain.ErrorCodeException
import com.eunmin.arrow.domain.LimitExceededException
import com.eunmin.arrow.domain.UserNotFoundException
import com.eunmin.arrow.dto.ErrorPayload
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(DuplicatedUserException::class)
    fun handleDuplicatedUserException(e: ErrorCodeException, request: WebRequest)
            = handleExceptionInternal(e, ErrorPayload.fromDomain(e), HttpHeaders(), HttpStatus.CONFLICT, request)

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: ErrorCodeException, request: WebRequest)
            = handleExceptionInternal(e, ErrorPayload.fromDomain(e), HttpHeaders(), HttpStatus.NOT_FOUND, request)

    @ExceptionHandler(LimitExceededException::class)
    fun handleLimitExceededException(e: ErrorCodeException, request: WebRequest)
            = handleExceptionInternal(e, ErrorPayload.fromDomain(e), HttpHeaders(), HttpStatus.BAD_REQUEST, request)
}