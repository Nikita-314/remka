package com.remka.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserAccount(
    val id: String,
    val email: String,
    val displayName: String = email.substringBefore("@"),
    val passwordHash: String? = null
)

@Serializable
data class SharedAccess(
    val userId: String,
    val note: String? = null
)

@Serializable
data class WorkAssignment(
    val userId: String,
    val description: String
)
