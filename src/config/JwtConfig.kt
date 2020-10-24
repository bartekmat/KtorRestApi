package com.gruzini.config

data class JwtConfig(val issuer: String, val realm: String, val secretKey: String, val expirationTime: Int)