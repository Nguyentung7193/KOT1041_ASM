package com.example.kot1041_asm.src.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kot1041_asm.src.DataStore.auth.TokenManager
import com.example.kot1041_asm.src.ui.compoment.Category.CategorySelector
import com.example.kot1041_asm.src.ui.compoment.product.ProductItem
import com.example.kot1041_asm.src.viewmodle.home.HomeViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    tokenManager: TokenManager
) {
    val state by viewModel.state.collectAsState()
    val token = runBlocking { tokenManager.getToken() ?: "" }

    var selectedCategory by remember { mutableStateOf("all") }
    LaunchedEffect(Unit) {
        viewModel.loadHomeData(token, selectedCategory)
    }
    LaunchedEffect(selectedCategory) {
        viewModel.loadHomeData(token, selectedCategory)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0D0D0D), Color(0xFF1F0036))))
            .padding(30.dp)
    ) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFCE93D8))
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error ?: "Có lỗi xảy ra", color = Color.Red)
            }
        } else {
            Column {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* TODO: Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Xin chào,", color = Color.LightGray, fontSize = 14.sp)
                        Text(
                            "Tung",
                            color = Color(0xFFE1BEE7),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Categories từ API
                val categoryNames = listOf("all") + state.categories.map { it.name }
                CategorySelector(
                    categories = categoryNames,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Product list từ API
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(state.products.chunked(2)) { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (product in rowItems) {
                                ProductItem(
                                    product = product,
                                    onClick = {
                                        navController.navigate("product_detail/${product._id}")
                                    },
                                    onAddToCart = { selectedProduct ->
                                        viewModel.addToCart(
                                            productId = product._id,
                                            quantity = 1,
                                            token = token
                                        ) // hoặc gọi API thêm giỏ hàng
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (rowItems.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}
