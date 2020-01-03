package com.dwelling.app.security.controller

import com.dwelling.app.security.JwtTokenUtil
import com.dwelling.app.security.JwtUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class UserRestController {

    @Value("\${jwt.header}")
    private lateinit var  tokenHeader: String


    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private val userDetailsService: UserDetailsService? = null

    @RequestMapping(value = ["user"], method = [RequestMethod.GET])
    fun getAuthenticatedUser(request: HttpServletRequest): JwtUser {
        val token = request.getHeader(tokenHeader).substring(7)
        val username = jwtTokenUtil.getUsernameFromToken(token)
        return userDetailsService!!.loadUserByUsername(username) as JwtUser
    }

}
