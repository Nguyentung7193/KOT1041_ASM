package com.example.kot1041_asm.src.model.auth

// Request models
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

// Response models
data class AuthResponse(
    val token: String?,     // trả về khi login/register thành công
    val message: String?,   // thông báo từ server
    val user: User?         // thông tin user nếu có
)

data class User(
    val _id: String,
    val name: String,
    val email: String
)
