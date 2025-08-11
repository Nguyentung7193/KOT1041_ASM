package com.example.kot1041_asm.src.viewmodle.home


import Category
import Product
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot1041_asm.src.config.RetrofitClient
import com.example.kot1041_asm.src.model.cart.CartResponse
import com.example.kot1041_asm.src.repository.home.HomeRepository
import com.example.kot1041_asm.src.services.home.HomeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val error: String? = null
)

class HomeViewModel : ViewModel() {

    private val repository = HomeRepository(RetrofitClient.instance.create(HomeService::class.java))

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val _addToCartState = MutableLiveData<Result<CartResponse>>()
    val addToCartState: LiveData<Result<CartResponse>> = _addToCartState

    fun loadHomeData(token: String, type: String = "all") {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val categoriesRes = repository.getCategories()
                val productsRes = repository.getProducts(type, token)

                if (categoriesRes.isSuccessful && productsRes.isSuccessful) {
                    _state.value = HomeState(
                        isLoading = false,
                        categories = categoriesRes.body()?.data ?: emptyList(),
                        products = productsRes.body()?.data ?: emptyList()
                    )
                } else {
                    Log.d("HomeViewModel", "Categories code: ${categoriesRes.code()}, body: ${categoriesRes.errorBody()?.string()}")
                    Log.d("HomeViewModel", "Products code: ${productsRes.code()}, body: ${productsRes.errorBody()?.string()}")

                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Không thể tải dữ liệu"

                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    fun addToCart(productId: String, quantity: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = repository.addToCart(productId, quantity, token)
                if (response.isSuccessful) {
                    _addToCartState.value = Result.success(response.body()!!)
                } else {
                    _addToCartState.value = Result.failure(
                        Exception(response.errorBody()?.string() ?: "Thêm vào giỏ hàng thất bại")
                    )
                }
            } catch (e: Exception) {
                _addToCartState.value = Result.failure(e)
            }
        }
    }

}
