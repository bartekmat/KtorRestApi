package com.gruzini.authentication

import com.gruzini.repositories.UserRepository
import io.ktor.auth.jwt.*

class CredentialValidator(private val repository: UserRepository) {
    fun validate(cred: JWTCredential): JWTPrincipal? {
        return if (userExists(cred) && passwordIsCorrect(cred)) {
            JWTPrincipal(cred.payload)
        } else {
            null
        }
    }

    private fun userExists(jwtCredential: JWTCredential): Boolean {
        val user = repository.fetchByUsername(getUsername(jwtCredential))
        return user != null
    }

    private fun passwordIsCorrect(jwtCredential: JWTCredential): Boolean {
        val user = repository.fetchByUsername(getUsername(jwtCredential))!!
        return getPassword(jwtCredential) == user.password
    }

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
