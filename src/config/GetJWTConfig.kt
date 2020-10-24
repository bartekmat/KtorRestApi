package com.gruzini.config

import io.ktor.application.*

fun getJWTConfig(environment: ApplicationEnvironment) = JwtConfig(
    issuer = environment.config.property("jwt.issuer").getString(),
    secretKey = environment.config.property("jwt.secret").getString(),
    realm = environment.config.property("jwt.realm").getString(),
    expirationTime = environment.config.property("jwt.validity_ms").getString().toInt()
)