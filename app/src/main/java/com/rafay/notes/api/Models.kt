package com.rafay.notes.api

import com.rafay.notes.domain.models.LocalProfile
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @field:Json(name = "token") val token: String,
    @field:Json(name = "profile") val profile: Profile
) {

    @JsonClass(generateAdapter = true)
    data class Profile(
        @field:Json(name = "first_name") val firstName: String,
        @field:Json(name = "last_name") val lastName: String,
        @field:Json(name = "dob") val dob: String,
        @field:Json(name = "email") val email: String
    )
}

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    @field:Json(name = "first_name") val firstName: String,
    @field:Json(name = "last_name") val lastName: String?,
    @field:Json(name = "dob") val dob: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") val password: String
)

@JsonClass(generateAdapter = true)
data class RegisterResponse(
    @field:Json(name = "token") val token: String,
    @field:Json(name = "profile") val profile: LoginResponse.Profile
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "password") val password: String
)

fun LoginResponse.Profile.toLocalProfile() = LocalProfile(
    firstName = firstName,
    lastName = lastName,
    email = email,
    dob = dob
)