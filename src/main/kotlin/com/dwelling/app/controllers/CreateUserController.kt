package com.dwelling.app.controllers

import com.dwelling.app.repository.VisitorPreferencesRepository
import com.dwelling.app.security.model.Authority
import com.dwelling.app.security.model.AuthorityName
import com.dwelling.app.security.model.User
import com.dwelling.app.security.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*


@RestController
class CreateUserController {
    @Autowired
    private lateinit var encoder: PasswordEncoder
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var visitorPreferencesRepository: VisitorPreferencesRepository

    @PostMapping("/auth/create-user")
    fun createUser(@RequestParam username: String, @RequestParam password: String, @RequestParam firstName: String, @RequestParam lastName: String, @RequestParam email: String) {
        var authority = Authority(1, AuthorityName.ROLE_USER,null)
        val user = User(-1, username, encoder. encode(password), firstName, lastName, email, true, Date(), listOf(authority))
        userRepository.save(user)
    }

}
