package com.example.kot1041_asm.src.viewmodle.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.kot1041_asm.src.model.order.OrderListModel
import com.example.kot1041_asm.src.model.order.OrderDetailModel
import com.example.kot1041_asm.src.services.order.OrderService
import com.example.kot1041_asm.src.repository.order.OrderRepository
import com.example.kot1041_asm.src.config.RetrofitClient

class OrderViewModel : ViewModel() {
    private val orderService = RetrofitClient.instance.create(OrderService::class.java)
    private val repository = OrderRepository(orderService)

    private val _orders = MutableStateFlow<List<OrderListModel>>(emptyList())
    val orders: StateFlow<List<OrderListModel>> = _orders

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _orderDetail = MutableStateFlow<OrderDetailModel?>(null)
    val orderDetail: StateFlow<OrderDetailModel?> = _orderDetail
    private val _orderDetailError = MutableStateFlow<String?>(null)
    val orderDetailError: StateFlow<String?> = _orderDetailError

    fun fetchOrders(token: String) {
        viewModelScope.launch {
            val result = repository.getAllOrders(token)
            if (result.isSuccess) {
                _orders.value = result.getOrDefault(emptyList())
                _error.value = null
            } else {
                _orders.value = emptyList()
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun fetchOrderDetail(token: String, orderId: String) {
        viewModelScope.launch {
            val result = repository.getOrderDetail(token, orderId)
            if (result.isSuccess) {
                _orderDetail.value = result.getOrNull()
                _orderDetailError.value = null
            } else {
                _orderDetail.value = null
                _orderDetailError.value = result.exceptionOrNull()?.message
            }
        }
    }
}
