package com.dwelling.app.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest


@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {


    override fun commence(
            request: HttpServletRequest,
            response: HttpServletResponse,
            authException: AuthenticationException?) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }


}
