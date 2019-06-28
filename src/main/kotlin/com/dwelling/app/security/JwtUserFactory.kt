package com.dwelling.app.security

import com.dwelling.app.security.model.Authority
import com.dwelling.app.security.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

object JwtUserFactory {

    fun create(user: User): JwtUser {
        return JwtUser(
                user.id,
                user.username,
                user.firstname,
                user.lastname,
                user.email,
                user.password,
                mapToGrantedAuthorities(user.authorities),
                user.enabled,
                user.lastPasswordResetDate
        )
    }

    private fun mapToGrantedAuthorities(authorities: List<Authority>): List<GrantedAuthority> {
        return authorities.stream()
                .map { authority -> SimpleGrantedAuthority(authority.name.name) }
                .collect(Collectors.toList())
    }
}
