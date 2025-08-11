package com.example.kot1041_asm.src.services.home

import CategoryResponse
import ProductDetailResponse
import ProductResponse
import com.example.kot1041_asm.src.model.cart.AddToCartRequest
import com.example.kot1041_asm.src.model.cart.CartResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeService {

    @GET("categories")
    suspend fun getCategories(): Response<CategoryResponse>


    @GET("products/type/{type}")
    suspend fun getProductsByType(
        @Path("type") type: String,
        @Header("Authorization") token: String
    ): Response<ProductResponse>

    @POST("cart/add")
    suspend fun addToCart(
        @Body request: AddToCartRequest,
        @Header("Authorization") token: String
    ): Response<CartResponse>

    @GET("products/detail/{id}")
    suspend fun getProductDetail(
        @Path("id") productId: String,
        @Header("Authorization") token: String
    ): Response<ProductDetailResponse>
}
