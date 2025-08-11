package com.example.kot1041_asm.src.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kot1041_asm.src.model.order.OrderListModel
import com.example.kot1041_asm.src.viewmodle.order.OrderViewModel
import com.example.kot1041_asm.src.DataStore.auth.TokenManager

@Composable
fun OrderHistoryScreen(
    onBack: () -> Unit,
    onOrderClick: (OrderListModel) -> Unit
) {
    val backgroundColor = Color(0xFF121212)
    val textColor = Color.White
    val accentColor = Color(0xFFD1AFFF)

    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    val token = kotlinx.coroutines.runBlocking { tokenManager.getToken() ?: "" }
    val orderViewModel: OrderViewModel = viewModel()
    val orders by orderViewModel.orders.collectAsState()
    val error by orderViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        orderViewModel.fetchOrders(token)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Custom App Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = accentColor
                )
            }
            Text(
                text = "Lịch sử đơn hàng",
                style = MaterialTheme.typography.headlineSmall,
                color = textColor
            )
        }

        if (error != null) {
            Text(text = error ?: "Lỗi không xác định", color = Color.Red)
        } else if (orders.isEmpty()) {
            Text(text = "Không có đơn hàng nào", color = Color.LightGray)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(orders) { order ->
                    OrderCard(order, onClick = { onOrderClick(order) }, textColor, accentColor)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    order: OrderListModel,
    onClick: () -> Unit,
    textColor: Color,
    accentColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Ngày: ${order.createdAt}", style = MaterialTheme.typography.titleSmall, color = textColor)

            Spacer(modifier = Modifier.height(8.dp))

            order.items.forEach {
                Text(
                    "- ${it.product} x${it.quantity}",
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Tổng: ${order.totalPrice}đ",
                style = MaterialTheme.typography.bodyLarge,
                color = accentColor
            )
        }
    }
}
