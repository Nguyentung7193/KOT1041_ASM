package com.example.kot1041_asm.src.repository.order
import com.example.kot1041_asm.src.model.order.OrderListModel
import com.example.kot1041_asm.src.model.order.OrderDetailModel
import com.example.kot1041_asm.src.services.order.OrderService
import retrofit2.Response

class OrderRepository(private val orderService: OrderService) {
    suspend fun getAllOrders(token: String): Result<List<OrderListModel>> {
        val authHeader = "Bearer $token"
        return try {
            val response = orderService.getAllOrders(authHeader)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrderDetail(token: String, orderId: String): Result<OrderDetailModel> {
        val authHeader = "Bearer $token"
        return try {
            val response = orderService.getOrderDetail(authHeader, orderId)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("Empty body"))
            } else {
                Result.failure(Exception("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}