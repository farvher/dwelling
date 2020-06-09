package com.dwelling.app.security.controller

import com.dwelling.app.security.JwtAuthenticationRequest
import com.dwelling.app.security.JwtTokenUtil
import com.dwelling.app.security.JwtUser
import com.dwelling.app.security.services.JwtAuthenticationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest


@RestController
class AuthenticationRestController {

    @Value("\${jwt.header}")
    private val tokenHeader: String? = null

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var  jwtTokenUtil: JwtTokenUtil

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private val userDetailsService: UserDetailsService? = null


    @PostMapping("/auth")
    @Throws(AuthenticationException::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtAuthenticationRequest): ResponseEntity<*> {

        authenticate(authenticationRequest.username!!, authenticationRequest.password!!)
        val userDetails = userDetailsService!!.loadUserByUsername(authenticationRequest.username)
        val token = jwtTokenUtil.generateToken(userDetails)

        return ResponseEntity.ok<Any>(JwtAuthenticationResponse(token))
    }

    @RequestMapping(value = ["/refresh"], method = [RequestMethod.GET])
    fun refreshAndGetAuthenticationToken(request: HttpServletRequest): ResponseEntity<*> {
        val authToken = request.getHeader(tokenHeader)
        val token = authToken.substring(7)
        val username = jwtTokenUtil.getUsernameFromToken(token)
        val user = userDetailsService!!.loadUserByUsername(username) as JwtUser

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.lastPasswordResetDate)!!) {
            val refreshedToken = jwtTokenUtil.refreshToken(token)
            return ResponseEntity.ok<Any>(JwtAuthenticationResponse(refreshedToken))
        } else {
            return ResponseEntity.badRequest().body<Any>(null)
        }
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
    }

    /**
     * Authenticates the user. If something is wrong, an [AuthenticationException] will be thrown
     */
     fun authenticate(username: String, password: String) {
        Objects.requireNonNull(username)
        Objects.requireNonNull(password)

        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw AuthenticationException("User is disabled!", e)
        } catch (e: BadCredentialsException) {
            throw AuthenticationException("Bad credentials!", e)
        }

    }
}


