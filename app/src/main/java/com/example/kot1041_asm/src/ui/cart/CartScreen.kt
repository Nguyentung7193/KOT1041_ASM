package com.example.kot1041_asm.src.ui.cart

import CartItemComposable
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.kot1041_asm.src.DataStore.auth.TokenManager
import com.example.kot1041_asm.src.model.cart.CartItem
import com.example.kot1041_asm.src.viewmodle.cart.CartViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = viewModel(),
    onBack: () -> Unit,
    onIncrease: (CartItem) -> Unit,
    onDecrease: (CartItem) -> Unit,
    onRemove: (CartItem) -> Unit,
    tokenManager: TokenManager
) {
    var showCheckoutDialog by remember { mutableStateOf(false) }
    var fullname by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("online") }
    val context = LocalContext.current
    val checkoutResult by cartViewModel.checkoutResult.observeAsState()
    // Load giỏ hàng khi màn hình khởi tạo
    val token = runBlocking { tokenManager.getToken() ?: "" }
    LaunchedEffect(Unit) {
        cartViewModel.loadCart(token)
    }

    // Lấy trạng thái hiện tại từ ViewModel
    val state by cartViewModel.state.collectAsState()

    val cartItems = state.cartData?.items ?: emptyList()

    val total = cartItems.sumOf { it.product?.price ?: 0 * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(
                text = "Giỏ hàng",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD1AFFF)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            // Hiện loading nếu cần
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), color = Color(0xFFBA68C8))
        } else if (state.error != null) {
            Text(
                text = state.error ?: "Lỗi không xác định",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (cartItems.isEmpty()) {
            Text(
                text = "Giỏ hàng trống",
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            // List giỏ hàng
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems) { item ->
                    CartItemComposable(
                        cartItem = item,
                        onIncrease = { onIncrease(item) },
                        onDecrease = { onDecrease(item) },
                        onRemove = { onRemove(item) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tổng tiền + Checkout
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tổng: $total đ",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        showCheckoutDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA68C8)),
                    modifier = Modifier
                        .height(48.dp)
                        .width(160.dp)
                ) {
                    Text(text = "Thanh toán", color = Color.White)
                }
            }
        }
    }



    // Hiển thị thông báo khi thanh toán thành công/thất bại
    LaunchedEffect(checkoutResult) {
        checkoutResult?.let {
            if (it.isSuccess) {
                Toast.makeText(context, "Thanh toán thành công!", Toast.LENGTH_LONG).show()
                showCheckoutDialog = false
                cartViewModel.loadCart(token)
            } else {
                Toast.makeText(context, "Thanh toán thất bại!", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Dialog nhập thông tin thanh toán
    if (showCheckoutDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            title = { Text("Thông tin thanh toán") },
            text = {
                Column {
                    androidx.compose.material3.OutlinedTextField(
                        value = fullname,
                        onValueChange = { fullname = it },
                        label = { Text("Họ tên") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    androidx.compose.material3.OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Địa chỉ") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    androidx.compose.material3.OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Số điện thoại") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    androidx.compose.material3.OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Ghi chú") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // Loại đơn hàng
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Loại đơn hàng:", modifier = Modifier.padding(end = 8.dp))
                        androidx.compose.material3.RadioButton(
                            selected = type == "online",
                            onClick = { type = "online" }
                        )
                        Text("Online", modifier = Modifier.padding(end = 8.dp))
                        androidx.compose.material3.RadioButton(
                            selected = type == "cod",
                            onClick = { type = "cod" }
                        )
                        Text("COD")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val body = mapOf(
                        "fullname" to fullname,
                        "address" to address,
                        "phone" to phone,
                        "note" to note,
                        "type" to type
                    )
                    cartViewModel.checkoutOrder(token, body)
                }) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                Button(onClick = { showCheckoutDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}
