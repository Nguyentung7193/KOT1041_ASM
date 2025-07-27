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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kot1041_asm.src.data.product.FakeData.fakeOrderHistory
import com.example.kot1041_asm.src.data.product.OrderHistory

@Composable
fun OrderHistoryScreen(
    onBack: () -> Unit,
    onOrderClick: (OrderHistory) -> Unit
) {
    val backgroundColor = Color(0xFF121212)
    val textColor = Color.White
    val accentColor = Color(0xFFD1AFFF)

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

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(fakeOrderHistory) { order ->
                OrderCard(order, onClick = { onOrderClick(order) }, textColor, accentColor)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun OrderCard(
    order: OrderHistory,
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
            Text("Ngày: ${order.date}", style = MaterialTheme.typography.titleSmall, color = textColor)

            Spacer(modifier = Modifier.height(8.dp))

            order.items.forEach {
                Text(
                    "- ${it.name} x${it.quantity} (${it.price.toInt()}đ)",
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Tổng: ${order.total.toInt()}đ",
                style = MaterialTheme.typography.bodyLarge,
                color = accentColor
            )
        }
    }
}
