package com.example.kot1041_asm.src.ui.compoment.product

import Product
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Model mới dùng API
data class ProductUiModel(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String
)

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    onAddToCart: (ProductUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = product.name,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.name,
            color = Color(0xFFD1AFFF),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${product.price} đ",
                color = Color.White,
                fontSize = 16.sp
            )
            IconButton(onClick = { onAddToCart(ProductUiModel(
                id = product._id,
                name = product.name,
                price = product.price,
                imageUrl = product.image
            )) }) {
                Icon(
                    Icons.Default.AddShoppingCart,
                    contentDescription = "Add to cart",
                    tint = Color(0xFFBA68C8)
                )
            }
        }
    }
}

