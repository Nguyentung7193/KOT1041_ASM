package com.example.kot1041_asm.src.viewmodle.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot1041_asm.src.config.RetrofitClient
import com.example.kot1041_asm.src.model.auth.AuthResponse
import com.example.kot1041_asm.src.repository.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: AuthResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository(RetrofitClient.authService)

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    _state.value = LoginState.Success(response.body()!!)
                } else {
                    _state.value = LoginState.Error(
                        response.errorBody()?.string() ?: "Đăng nhập thất bại"
                    )
                }
            } catch (e: Exception) {
                _state.value = LoginState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}
