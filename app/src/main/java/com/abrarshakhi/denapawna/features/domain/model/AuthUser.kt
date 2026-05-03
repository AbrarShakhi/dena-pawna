package com.abrarshakhi.denapawna.features.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    val id: String,
    val displayName: String,
    val email: String,
    val phone: String? = null,
)
