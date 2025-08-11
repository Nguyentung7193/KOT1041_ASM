package com.example.kot1041_asm.src.viewmodle.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot1041_asm.src.config.RetrofitClient
import com.example.kot1041_asm.src.model.cart.CartData
import com.example.kot1041_asm.src.model.cart.CartResponseCart
import com.example.kot1041_asm.src.repository.cart.CartRepository
import com.example.kot1041_asm.src.repository.home.HomeRepository
import com.example.kot1041_asm.src.services.cart.CartService
import com.example.kot1041_asm.src.services.home.HomeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

data class CartState(
    val isLoading: Boolean = false,
    val cartData: CartData? = null,
    val error: String? = null
)

class CartViewModel() : ViewModel() {
    private val repository = CartRepository(RetrofitClient.instance.create(CartService::class.java))

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()
    private val _updateCartResult = MutableLiveData<Result<CartResponseCart>>()
    val updateCartResult: LiveData<Result<CartResponseCart>> = _updateCartResult
    private val _checkoutResult = MutableLiveData<Result<Any>>()
    val checkoutResult: LiveData<Result<Any>> = _checkoutResult

    fun loadCart(token: String) {
        viewModelScope.launch {
            _state.value = CartState(isLoading = true)
            try {
                val response: Response<CartResponseCart> = repository.getCart(token)
                if (response.isSuccessful) {
                    _state.value = CartState(
                        isLoading = false,
                        cartData = response.body()?.data
                    )
                } else {
                    _state.value = CartState(
                        isLoading = false,
                        error = "Lỗi tải giỏ hàng: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _state.value = CartState(
                    isLoading = false,
                    error = e.localizedMessage ?: "Lỗi không xác định"
                )
            }
        }
    }
    fun updateCartItem(token: String, productId: String, quantity: Int) {
        viewModelScope.launch {
            val result = repository.updateCartItem(token, productId, quantity)
            _updateCartResult.postValue(result)
        }
    }

    fun checkoutOrder(token: String, body: Map<String, String>) {
        viewModelScope.launch {
            val result = repository.checkoutOrder(token, body)
            _checkoutResult.postValue(result)
        }
    }
}
