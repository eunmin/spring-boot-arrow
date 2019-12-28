package com.eunmin.arrow.controller

import com.eunmin.arrow.domain.DuplicatedUserException
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
    fun get(@PathVariable("id") id: String): UserPayload = userService.get(id)

    @PostMapping("/")
    fun create(@RequestBody input: CreateUserInput): UserPayload = userService.create(input)
}