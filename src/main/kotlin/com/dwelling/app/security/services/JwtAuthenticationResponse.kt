package com.dwelling.app.security.services

import java.io.Serializable

class JwtAuthenticationResponse(val token: String) : Serializable {
    companion object {
        private const val serialVersionUID = 1250166508152483573L
    }
}
