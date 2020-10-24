package com.gruzini.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gruzini.models.User
import java.util.*

fun generateJWT(user: User, jwtConfig: JwtConfig): String = JWT.create()
    .withSubject("Authentication")
    .withIssuer(jwtConfig.issuer)
    .withClaim("name", user.name)
    .withClaim("password", user.password)
    .withExpiresAt(obtainExpirationDate(jwtConfig.expirationTime))
    .sign(Algorithm.HMAC256(jwtConfig.secretKey))

fun obtainExpirationDate(expirationTime: Int) = Date(System.currentTimeMillis() + expirationTime)