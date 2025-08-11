package com.example.kot1041_asm.src.ui.product

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kot1041_asm.src.DataStore.auth.TokenManager
import com.example.kot1041_asm.src.ui.compoment.quantity.QuantitySelector
import com.example.kot1041_asm.src.viewmodle.productDetail.ProductDetailViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun ProductDetailScreen(
    productId: String,
    onBack: () -> Unit,
    tokenManager: TokenManager,
    viewModel: ProductDetailViewModel = viewModel()
) {
    val token = runBlocking { tokenManager.getToken() ?: "" }
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Lắng nghe kết quả addToCart từ ViewModel
    LaunchedEffect(Unit) {
        viewModel.addToCartResult.collect { result ->
            result.onSuccess {
                snackbarHostState.showSnackbar("Thêm vào giỏ hàng thành công!")
            }
            result.onFailure {
                snackbarHostState.showSnackbar("Lỗi: ${it.message}")
            }
        }
    }

    // Load chi tiết sản phẩm khi productId thay đổi
    LaunchedEffect(productId) {
        viewModel.loadProductDetail(productId, token)
    }

    var quantity by remember { mutableStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFFBA68C8))
            }
            state.error != null -> {
                Text(
                    text = state.error ?: "Lỗi không xác định",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.productDetail != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Ảnh sản phẩm", color = Color.LightGray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = state.productDetail!!.name,
                        color = Color(0xFFBA68C8),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${state.productDetail!!.price}₫",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        QuantitySelector(quantity = quantity, onQuantityChange = { quantity = it })
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = state.productDetail!!.description,
                        color = Color(0xFFDDDDDD),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF2C2C2C))
                                .clickable {
                                    isFavorite = !isFavorite
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            if (isFavorite) "Đã thêm vào yêu thích" else "Đã xoá khỏi yêu thích"
                                        )
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color(0xFFFF4081) else Color.White
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.addToCart(
                                    productId = productId,
                                    quantity = quantity,
                                    token = token
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFBA68C8),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                        ) {
                            Text("Thêm vào giỏ hàng", fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}



