package com.example.kot1041_asm.src.ui.compoment.Product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.kot1041_asm.src.data.product.Product

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // 👉 THÊM dòng này
) {
    Column(
        modifier = modifier
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            modifier = Modifier
                .height(200.dp)
//                .fillMaxHeight()
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
            IconButton(onClick = { /* TODO: add to cart */ }) {
                Icon(
                    Icons.Default.AddShoppingCart,
                    contentDescription = "Add to cart",
                    tint = Color(0xFFBA68C8)
                )
            }
        }
    }
}
