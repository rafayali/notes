package com.rafay.notes.api

import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}
