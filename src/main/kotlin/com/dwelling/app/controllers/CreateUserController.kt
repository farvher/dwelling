package com.dwelling.app.controllers

import com.dwelling.app.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class CreateUserController(
    private val userRepository: UserRepository
) {

    val logger: Logger = LoggerFactory.getLogger(CreateUserController::class.java)


    @PostMapping("/auth/create-user")
    fun createUser(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam email: String
    ) {

    }

}
