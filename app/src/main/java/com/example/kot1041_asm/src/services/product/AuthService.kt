package com.example.kot1041_asm.src.services.product

import com.example.kot1041_asm.src.model.auth.AuthResponse
import com.example.kot1041_asm.src.model.auth.LoginRequest
import com.example.kot1041_asm.src.model.auth.RegisterRequest
import com.example.kot1041_asm.src.model.auth.User
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>

    @GET("auth/getInforUser")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<User>
}
