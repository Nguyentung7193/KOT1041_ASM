package com.example.kot1041_asm.src.model.order

// Model cho danh sách đơn hàng

data class OrderListItemModel(
    val product: String,
    val quantity: Int,
    val _id: String
)

data class OrderListModel(
    val _id: String,
    val userID: String,
    val fullname: String,
    val address: String,
    val phone: String,
    val totalPrice: Int,
    val note: String?,
    val type: String,
    val items: List<OrderListItemModel>,
    val createdAt: String,
    val updatedAt: String
)

// Model cho chi tiết đơn hàng

data class OrderProductModel(
    val _id: String,
    val name: String,
    val type: String,
    val price: Int,
    val description: String?,
    val createdAt: String?,
    val updatedAt: String?
)

data class OrderDetailItemModel(
    val product: OrderProductModel,
    val quantity: Int,
    val _id: String
)

data class OrderDetailModel(
    val _id: String,
    val userID: String,
    val fullname: String,
    val address: String,
    val phone: String,
    val totalPrice: Int,
    val note: String?,
    val type: String,
    val items: List<OrderDetailItemModel>,
    val createdAt: String,
    val updatedAt: String
)
