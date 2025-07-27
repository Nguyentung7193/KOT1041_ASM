package com.example.kot1041_asm.src.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.kot1041_asm.src.data.product.FakeData
import com.example.kot1041_asm.src.data.product.FakeData.products
import com.example.kot1041_asm.src.ui.compoment.Category.CategorySelector
import com.example.kot1041_asm.src.ui.compoment.Product.ProductItem


@Composable
fun HomeScreen(navController: NavController) {
//    var selectedCategory by remember { mutableStateOf(FakeData.categories.first()) }
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    val filteredProducts = remember(selectedCategory, products) {
        if (selectedCategory == "Tất cả") {
            products
        } else {
            products.filter { it.category == selectedCategory }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0D0D0D), Color(0xFF1F0036))))
            .padding(30.dp)
    ) {
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
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Categories
            CategorySelector(
                categories = FakeData.categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Product list
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredProducts.chunked(2)) { rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (product in rowItems) {
                            ProductItem(
                                product = product,
                                onClick = {
                                    navController.navigate("product_detail/${product.name}")
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Nếu chỉ có 1 item trong hàng → thêm 1 Spacer để căn đều
                        if (rowItems.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}