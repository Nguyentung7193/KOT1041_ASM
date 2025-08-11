package com.example.kot1041_asm.src.repository.cart

import com.example.kot1041_asm.src.model.cart.CartResponse
import com.example.kot1041_asm.src.model.cart.CartResponseCart
import com.example.kot1041_asm.src.services.cart.CartService
import retrofit2.Response

class CartRepository(private val cartService: CartService) {

    suspend fun getCart(token: String): Response<CartResponseCart> {
        val authHeader = "Bearer $token"
        return cartService.getCart(authHeader)
    }
    suspend fun updateCartItem(token: String, productId: String, quantity: Int): Result<CartResponseCart> {
        return try {
            val response = cartService.updateCart(productId, quantity, "Bearer $token")
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun checkoutOrder(token: String, body: Map<String, String>): Result<Any> {
        return try {
            val response = cartService.checkoutOrder("Bearer $token", body)
            if (response.isSuccessful) {
                Result.success(response.body() ?: "Success")
            } else {
                Result.failure(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
