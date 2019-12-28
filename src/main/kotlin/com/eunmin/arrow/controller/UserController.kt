package com.eunmin.arrow.controller

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eunmin.arrow.dto.CreateUserInput
import com.eunmin.arrow.dto.UserPayload
import com.eunmin.arrow.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(
        private val userService: UserService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): UserPayload {
        val userPayload = userService.get(id).unsafeRunSync()
        return when(userPayload) {
            is Either.Right -> userPayload.b
            is Either.Left -> throw userPayload.a
        }
    }

    @PostMapping("/")
    fun create(@RequestBody input: CreateUserInput): UserPayload {
        val userPayload = userService.create(input).unsafeRunSync()
        return when(userPayload) {
            is Either.Right -> userPayload.b
            is Either.Left -> throw userPayload.a
        }
    }
}