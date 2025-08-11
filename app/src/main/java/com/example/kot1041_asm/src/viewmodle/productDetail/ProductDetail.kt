package com.example.kot1041_asm.src.viewmodle.productDetail

import ProductDetail
import ProductDetailResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kot1041_asm.src.config.RetrofitClient
import com.example.kot1041_asm.src.model.cart.CartResponse
import com.example.kot1041_asm.src.repository.home.HomeRepository
import com.example.kot1041_asm.src.services.home.HomeService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

data class ProductDetailState(
    val isLoading: Boolean = false,
    val productDetail: ProductDetail? = null,
    val error: String? = null
)

class ProductDetailViewModel() : ViewModel() {
    private val repository = HomeRepository(RetrofitClient.instance.create(HomeService::class.java))

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()
    private val _addToCartResult = MutableSharedFlow<Result<CartResponse>>()
    val addToCartResult = _addToCartResult.asSharedFlow()

    fun loadProductDetail(productId: String, token: String) {
        viewModelScope.launch {
            _state.value = ProductDetailState(isLoading = true)
            try {
                val response: Response<ProductDetailResponse> = repository.getProductDetail(productId, token)
                if (response.isSuccessful) {
                    _state.value = ProductDetailState(
                        isLoading = false,
                        productDetail = response.body()?.data
                    )
                } else {
                    _state.value = ProductDetailState(
                        isLoading = false,
                        error = "Lỗi tải dữ liệu: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _state.value = ProductDetailState(
                    isLoading = false,
                    error = e.localizedMessage ?: "Lỗi không xác định"
                )
            }
        }
    }
    fun addToCart(productId: String, quantity: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = repository.addToCart(productId, quantity, token)
                if (response.isSuccessful) {
                    _addToCartResult.emit(Result.success(response.body()!!))
                } else {
                    _addToCartResult.emit(Result.failure(Exception(response.errorBody()?.string() ?: "Thêm vào giỏ hàng thất bại")))
                }
            } catch (e: Exception) {
                _addToCartResult.emit(Result.failure(e))
            }
        }
    }
}
