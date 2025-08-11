package com.example.kot1041_asm.src.model.cart

import com.google.gson.annotations.SerializedName

data class AddToCartRequest(
    val productId: String,
    val quantity: Int
)

data class CartResponse(
    val message: String,
    val cart: Any // nếu có model giỏ hàng thì thay Any
)
data class CartResponseCart(
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("data") val data: CartData?
)

data class CartData(
    @SerializedName("_id") val id: String,
    @SerializedName("userID") val userId: String,
    @SerializedName("items") val items: List<CartItem>,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("__v") val v: Int
)

data class CartItem(
    @SerializedName("_id") val id: String,
    @SerializedName("product") val product: Product?,
    @SerializedName("quantity") val quantity: Int
)

data class Product(
    @SerializedName("_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("price") val price: Int,
    @SerializedName("image") val image: String,
    @SerializedName("description") val description: String,
    @SerializedName("__v") val v: Int,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
