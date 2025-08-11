package com.example.kot1041_asm.src.repository.home

import ProductDetailResponse
import com.example.kot1041_asm.src.model.cart.AddToCartRequest
import com.example.kot1041_asm.src.model.cart.CartResponse
import com.example.kot1041_asm.src.services.home.HomeService
import retrofit2.Response


class HomeRepository(private val homeService: HomeService) {

    suspend fun getCategories() = homeService.getCategories()

    suspend fun getProducts(type: String, token: String) =
        homeService.getProductsByType(type, "Bearer $token")

    suspend fun addToCart(productId: String, quantity: Int, token: String): Response<CartResponse> {
        val request = AddToCartRequest(productId, quantity)
        return homeService.addToCart(request, "Bearer $token")
    }
    suspend fun getProductDetail(productId: String, token: String): Response<ProductDetailResponse> {
        return homeService.getProductDetail(productId, "Bearer $token")
    }
}
