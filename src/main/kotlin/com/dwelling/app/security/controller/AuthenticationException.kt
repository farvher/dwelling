package com.dwelling.app.security.controller

class AuthenticationException : Exception {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
