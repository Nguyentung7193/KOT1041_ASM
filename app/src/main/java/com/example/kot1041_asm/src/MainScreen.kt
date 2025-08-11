package com.example.kot1041_asm.src

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdfScanner
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kot1041_asm.src.navigation.AppNavHost

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun MainScreen(navController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItem("Trang chủ", "home", Icons.Default.Home),
        BottomNavItem("Giỏ hàng", "cart", Icons.Default.ShoppingCart),
        BottomNavItem("Tài khoản", "profile", Icons.Default.Person)
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val backgroundColor = Color(0xFF121212)
    val selectedColor = Color(0xFFD1AFFF)
    val unselectedColor = Color(0xFFAAAAAA)

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route }) {
                NavigationBar(
                    containerColor = backgroundColor,
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label,
                                    tint = if (selected) selectedColor else unselectedColor
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    color = if (selected) selectedColor else unselectedColor
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo("home") {
                                        saveState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(backgroundColor)
        ) {
            AppNavHost(navController = navController)
        }
    }
}
