package com.dwelling.app.security.services

import com.dwelling.app.security.JwtUserFactory
import com.dwelling.app.security.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("jwtUserDetailsService")
class JwtUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository


    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByUsername(username)

        if (user != null) {
            return JwtUserFactory.create(user = user)
        } else {
            throw UsernameNotFoundException(String.format("No user found with username '%s'.", username))

        }
    }
}
