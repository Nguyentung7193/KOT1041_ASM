package com.example.kot1041_asm.src.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kot1041_asm.src.data.product.OrderHistory
import com.example.kot1041_asm.src.data.product.OrderItem

@Composable
fun OrderDetailScreen(
    order: OrderHistory,
    onBack: () -> Unit
) {
    val backgroundColor = Color(0xFF121212)
    val accentColor = Color(0xFFD1AFFF)
    val surfaceColor = Color(0xFF1E1E1E)
    val textColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = accentColor
                )
            }
            Text(
                text = "Chi tiết đơn hàng",
                style = MaterialTheme.typography.headlineSmall,
                color = textColor
            )
        }

        // Thông tin đơn hàng
        Text("🆔 Mã đơn hàng: ${order.id}", color = textColor)
        Text("📅 Ngày đặt: ${order.date}", color = textColor)
        Text("🚚 Trạng thái: ${order.status}", color = accentColor, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))

        Text("📦 Sản phẩm", style = MaterialTheme.typography.titleMedium, color = accentColor)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(order.items.size) { index ->
                OrderDetailItem(order.items[index])
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tổng tiền
        Text(
            text = "💰 Tổng tiền: ${order.total.toInt()}đ",
            style = MaterialTheme.typography.titleMedium,
            color = accentColor,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nút đặt lại
        Button(
            onClick = { /* TODO: đặt lại */ },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("🛒 Đặt lại", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun OrderDetailItem(item: OrderItem) {
    val surfaceColor = Color(0xFF1E1E1E)
    val textColor = Color.White
    val grayText = Color(0xFFBBBBBB)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name, color = textColor, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Giá: ${item.price.toInt()}đ", color = grayText)
            Text("Số lượng: ${item.quantity}", color = grayText)
        }
    }
}
