package com.example.kot1041_asm.src.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kot1041_asm.R

@Composable
fun ProfileScreen(
    userName: String = "Nguyen Tung",
    onEditProfile: () -> Unit = {},
    onViewHistory: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val backgroundColor = Color(0xFF121212)
    val textColor = Color.White
    val iconColor = Color(0xFFD1AFFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Image(
            painter = painterResource(id = R.drawable.raiden),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = userName,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium,
            color = textColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileOptionItem(
            icon = Icons.Default.Person,
            title = "Chỉnh sửa hồ sơ",
            onClick = onEditProfile,
            iconColor = iconColor,
            textColor = textColor
        )

        ProfileOptionItem(
            icon = Icons.Default.History,
            title = "Lịch sử đơn hàng",
            onClick = onViewHistory,
            iconColor = iconColor,
            textColor = textColor
        )

        ProfileOptionItem(
            icon = Icons.Default.ExitToApp,
            title = "Đăng xuất",
            onClick = onLogout,
            iconColor = iconColor,
            textColor = textColor
        )
    }
}

@Composable
fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    iconColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor
        )
    }
}
