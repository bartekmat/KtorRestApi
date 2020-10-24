package com.gruzini.authentication

import com.gruzini.services.UserService
import io.ktor.auth.jwt.*

class CredentialValidator (private val userService: UserService) {
    fun validate(cred: JWTCredential): JWTPrincipal? {
        return if (userExists(cred) && passwordIsCorrect(cred)) {
            JWTPrincipal(cred.payload)
        } else {
            null
        }
    }
    private fun userExists(jwtCredential: JWTCredential) =
        userService.userByLoginExists(getUsername(jwtCredential))

    private fun passwordIsCorrect(jwtCredential: JWTCredential): Boolean =
        userService.passwordIsCorrect(password = getPassword(jwtCredential), username = getUsername(jwtCredential))


    private fun getPassword(jwtCredential: JWTCredential): String {
        val password = jwtCredential.payload.getClaim("password").asString()!!
        println("password = $password")
        return password
    }

    private fun getUsername(jwtCredential: JWTCredential): String {
        val username = jwtCredential.payload.getClaim("name").asString()!!
        println("username = $username")
        return username
    }
}
