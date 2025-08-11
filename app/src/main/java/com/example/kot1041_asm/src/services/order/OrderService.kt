package com.example.kot1041_asm.src.services.order

import com.example.kot1041_asm.src.model.order.OrderListModel
import com.example.kot1041_asm.src.model.order.OrderDetailModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface OrderService {
    @GET("/api/orders/order")
    suspend fun getAllOrders(
        @Header("Authorization") token: String
    ): Response<List<OrderListModel>>

    @GET("/api/orders/order/{orderId}")
    suspend fun getOrderDetail(
        @Header("Authorization") token: String,
        @Path("orderId") orderId: String
    ): Response<OrderDetailModel>
}
