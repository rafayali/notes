package com.rafay.notes.domain.models

/**
 * Data class for storing loggedIn user profile information
 */
data class LocalProfile(
    val firstName: String,
    val lastName: String?,
    val email: String,
    val dob: String
)