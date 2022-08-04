package com.rafay.notes.domain.models

/**
 * Stores loggedIn user profile information
 */
data class LocalProfile(
    val firstName: String,
    val lastName: String?,
    val email: String,
    val dob: String
)
