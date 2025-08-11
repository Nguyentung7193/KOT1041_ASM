package com.example.kot1041_asm.src.repository.auth

import com.example.kot1041_asm.src.model.auth.AuthResponse
import com.example.kot1041_asm.src.model.auth.LoginRequest
import com.example.kot1041_asm.src.model.auth.RegisterRequest
import com.example.kot1041_asm.src.model.auth.User
import com.example.kot1041_asm.src.services.product.AuthService
import retrofit2.Response

class AuthRepository(private val authService: AuthService) {

    suspend fun login(email: String, password: String): Response<AuthResponse> {
        return authService.login(LoginRequest(email, password))
    }

    suspend fun register(name: String, email: String, password: String): Response<AuthResponse> {
        return authService.register(RegisterRequest(name, email, password))
    }

    suspend fun getUserInfo(token: String): Response<User> {
        return authService.getUserInfo("Bearer $token")
    }
}
