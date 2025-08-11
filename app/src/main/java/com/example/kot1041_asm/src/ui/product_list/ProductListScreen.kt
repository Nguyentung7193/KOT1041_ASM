//package com.example.kot1041_asm.src.ui.product_list
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.*
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.*
//import androidx.navigation.NavController
//import com.example.kot1041_asm.src.data.product.FakeData
//import com.example.kot1041_asm.src.ui.compoment.Product.ProductItem
//import androidx.compose.foundation.layout.FlowRow
//
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.ui.focus.onFocusChanged
//
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun ProductListScreen(
//    navController: NavController,
//    onBack: () -> Unit,
//    onCartClick: () -> Unit
//) {
//    var selectedCategory by remember { mutableStateOf("Tất cả") }
//    var searchQuery by remember { mutableStateOf("") }
//    var isSearchFocused by remember { mutableStateOf(false) }
//    val suggestedKeywords = listOf("Jean", "Hu Tao", "Raiden", "Furina")
//
//    val filteredProducts = remember(selectedCategory, searchQuery) {
//        FakeData.products.filter {
//            (selectedCategory == "Tất cả" || it.category == selectedCategory) &&
//                    it.name.contains(searchQuery, ignoreCase = true)
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Brush.verticalGradient(listOf(Color(0xFF0D0D0D), Color(0xFF1F0036))))
//            .padding(16.dp)
//    ) {
//        // Header
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            IconButton(onClick = onBack) {
//                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
//            }
//            Spacer(modifier = Modifier.weight(1f))
//            Text(
//                "Tất cả sản phẩm",
//                color = Color.White,
//                style = MaterialTheme.typography.titleLarge
//            )
//            Spacer(modifier = Modifier.weight(1f))
//            IconButton(onClick = onCartClick) {
//                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Search Box
//        Surface(
//            color = Color(0xFF1A1A1A),
//            shape = RoundedCornerShape(12.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
//            ) {
//                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
//                Spacer(modifier = Modifier.width(8.dp))
//                BasicTextField(
//                    value = searchQuery,
//                    onValueChange = { searchQuery = it },
//                    textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .onFocusChanged { focusState ->
//                            isSearchFocused = focusState.isFocused
//                        },
//                    singleLine = true,
//                    decorationBox = { innerTextField ->
//                        if (searchQuery.isEmpty()) {
//                            Text("Tìm kiếm...", color = Color.Gray)
//                        }
//                        innerTextField()
//                    }
//                )
//            }
//        }
//        // ✅ Suggested Chips đặt ngay dưới Search Box
//
//
//
//        if(isSearchFocused) {
//            Spacer(modifier = Modifier.height(12.dp))
//            FlowRow(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                suggestedKeywords.forEach { keyword ->
//                    AssistChip(
//                        onClick = { searchQuery = keyword },
//                        label = { Text(keyword, color = Color.White) },
//                        shape = RoundedCornerShape(20.dp),
//                        colors = AssistChipDefaults.assistChipColors(
//                            containerColor = Color(0xFF2E2E2E),
//                            labelColor = Color.White
//                        )
//                    )
//                }
//            }
//        }
//
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Category TabBar
//        ScrollableTabRow(
//            selectedTabIndex = FakeData.categories.indexOf(selectedCategory),
//            edgePadding = 0.dp,
//            contentColor = Color(0xFFE1BEE7),
//            containerColor = Color.Transparent,
//        ) {
//            FakeData.categories.forEachIndexed { index, category ->
//                Tab(
//                    selected = selectedCategory == category,
//                    onClick = { selectedCategory = category },
//                    text = {
//                        Text(
//                            category,
//                            color = if (selectedCategory == category) Color(0xFFE1BEE7) else Color.LightGray
//                        )
//                    }
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Product List
//        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//            items(filteredProducts.chunked(2)) { rowItems ->
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    for (product in rowItems) {
//                        ProductItem(
//                            product = product,
//                            onClick = {
//                                navController.navigate("product_detail/${product.name}")
//                            },
//                            modifier = Modifier.weight(1f)
//                        )
//                    }
//                    if (rowItems.size < 2) {
//                        Spacer(modifier = Modifier.weight(1f))
//                    }
//                }
//            }
//        }
//    }
//}
