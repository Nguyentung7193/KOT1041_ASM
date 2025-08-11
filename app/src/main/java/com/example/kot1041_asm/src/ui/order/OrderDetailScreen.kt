package com.example.kot1041_asm.src.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kot1041_asm.src.viewmodle.order.OrderViewModel
import com.example.kot1041_asm.src.DataStore.auth.TokenManager
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OrderDetailScreen(
    orderId: String,
    onBack: () -> Unit
) {
    val backgroundColor = Color(0xFF121212)
    val accentColor = Color(0xFFD1AFFF)
    val surfaceColor = Color(0xFF1E1E1E)
    val textColor = Color.White
    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    val token = kotlinx.coroutines.runBlocking { tokenManager.getToken() ?: "" }
    val orderViewModel: OrderViewModel = viewModel()
    val order by orderViewModel.orderDetail.collectAsState()
    val error by orderViewModel.orderDetailError.collectAsState()

    LaunchedEffect(orderId) {
        orderViewModel.fetchOrderDetail(token, orderId)
    }

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
        if (error != null) {
            Text(text = error ?: "Lỗi không xác định", color = Color.Red)
        } else if (order == null) {
            Text(text = "Đang tải...", color = Color.LightGray)
        } else {
            // Thông tin đơn hàng
            Text("🆔 Mã đơn hàng: ${order!!._id}", color = textColor)
            Text("👤 Người nhận: ${order!!.fullname}", color = textColor)
            Text("📞 SĐT: ${order!!.phone!!}", color = textColor)
            Text("🏠 Địa chỉ: ${order!!.address}", color = textColor)
            Text("📅 Ngày đặt: ${order!!.createdAt}", color = textColor)
            Text("🚚 Loại đơn hàng: ${order!!.type}", color = accentColor)
            if (!order!!.note.isNullOrBlank()) {
                Text("📝 Ghi chú: ${order!!.note}", color = accentColor)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("📦 Sản phẩm", style = MaterialTheme.typography.titleMedium, color = accentColor)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(order!!.items.size) { index ->
                    val item = order!!.items[index]
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = surfaceColor)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = item.product.name, color = textColor, style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Giá: ${item.product.price}đ", color = Color(0xFFBBBBBB))
                            Text("Số lượng: ${item.quantity}", color = Color(0xFFBBBBBB))
                            if (!item.product.description.isNullOrBlank()) {
                                Text("Mô tả: ${item.product.description}", color = Color(0xFFBBBBBB))
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "💰 Tổng tiền: ${order!!.totalPrice}đ",
                style = MaterialTheme.typography.titleMedium,
                color = accentColor,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
