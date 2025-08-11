package com.example.kot1041_asm.src.services.cart

import com.example.kot1041_asm.src.model.cart.CartResponse
import com.example.kot1041_asm.src.model.cart.CartResponseCart
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Query

interface CartService {


    @GET("/api/cart")
    suspend fun getCart(
        @Header("Authorization") token: String
    ): Response<CartResponseCart>

    @PUT("/api/cart/update")
    suspend fun updateCart(
        @Query("productId") productId: String,
        @Query("quantity") quantity: Int,
        @Header("Authorization") authHeader: String
    ): Response<CartResponseCart>

    @POST("/api/orders/checkout")
    suspend fun checkoutOrder(
        @Header("Authorization") authHeader: String,
        @retrofit2.http.Body body: Map<String, String>
    ): retrofit2.Response<Any>

}
