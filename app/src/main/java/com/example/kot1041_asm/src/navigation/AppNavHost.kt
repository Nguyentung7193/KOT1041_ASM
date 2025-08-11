package com.example.kot1041_asm.src.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kot1041_asm.src.DataStore.auth.TokenManager
import com.example.kot1041_asm.src.ui.auth.login.LoginScreen
import com.example.kot1041_asm.src.ui.auth.signup.RegisterScreen
import com.example.kot1041_asm.src.ui.cart.CartScreen
import com.example.kot1041_asm.src.ui.home.HomeScreen
import com.example.kot1041_asm.src.ui.product.ProductDetailScreen
import com.example.kot1041_asm.src.ui.profile.ProfileScreen
//import com.example.kot1041_asm.src.ui.cart.CartScreen
//import com.example.kot1041_asm.src.ui.home.HomeScreen
//import com.example.kot1041_asm.src.ui.order.OrderDetailScreen
//import com.example.kot1041_asm.src.ui.order.OrderHistoryScreen
//import com.example.kot1041_asm.src.ui.product.ProductDetailScreen
//import com.example.kot1041_asm.src.ui.product_list.ProductListScreen
//import com.example.kot1041_asm.src.ui.profile.ProfileScreen
import com.example.kot1041_asm.src.ui.splash.WelcomeScreen
import com.example.kot1041_asm.src.viewmodle.cart.CartViewModel
import kotlinx.coroutines.runBlocking


@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("login") {
           LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            val context = LocalContext.current
            val tokenManager = TokenManager(context)

            // Nếu token lấy từ DataStore
            val token by tokenManager.token.collectAsState(initial = "")

            HomeScreen(
                navController = navController,
                tokenManager = tokenManager
            )
        }
        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val context = LocalContext.current
            val tokenManager = TokenManager(context)
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
                tokenManager = tokenManager
            )
        }
        composable("cart") {
            val context = LocalContext.current
            val tokenManager = TokenManager(context)
            val cartViewModel = androidx.lifecycle.viewmodel.compose.viewModel<com.example.kot1041_asm.src.viewmodle.cart.CartViewModel>()
            val token = kotlinx.coroutines.runBlocking { tokenManager.getToken() ?: "" }
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() },
                onIncrease = { cartItem ->
                    val productId = cartItem.product?.id ?: return@CartScreen
                    val newQuantity = cartItem.quantity + 1
                    cartViewModel.updateCartItem(token, productId, newQuantity)
                    cartViewModel.loadCart(token)
                },
                onDecrease = { cartItem ->
                    val productId = cartItem.product?.id ?: return@CartScreen
                    val newQuantity = if (cartItem.quantity > 1) cartItem.quantity - 1 else 1
                    cartViewModel.updateCartItem(token, productId, newQuantity)
                    cartViewModel.loadCart(token)
                },
                onRemove = { cartItem ->
                    val productId = cartItem.product?.id ?: return@CartScreen
                    cartViewModel.updateCartItem(token, productId, 0)
                    cartViewModel.loadCart(token)
                },
                tokenManager = tokenManager
            )
        }
        composable(
            route = "order_detail/{orderId}",
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            com.example.kot1041_asm.src.ui.order.OrderDetailScreen(
                orderId = orderId,
                onBack = { navController.popBackStack() }
            )
        }
        composable("order_history") {
            com.example.kot1041_asm.src.ui.order.OrderHistoryScreen(
                onBack = { navController.popBackStack() },
                onOrderClick = { order ->
                    navController.navigate("order_detail/${order._id}")
                }
            )
        }

//        composable("home") {
//             HomeScreen(navController)
//        }
//        composable("product_detail/{productName}") { backStackEntry ->
//            val productName = backStackEntry.arguments?.getString("productName")
//            val product = FakeData.products.find { it.name == productName }
//            if (product != null) {
//                ProductDetailScreen(
//                    product = product,
//                    onBack = { navController.popBackStack() },
//                    onAddToCart = { prod, quantity ->
//                        // xử lý thêm vào giỏ
//                    }
//                )
//            }
//        }
//        composable("cart") {
//            val cartItems = remember {
//                mutableStateListOf<CartItem>().apply {
//                    addAll(fakeCartItems) // tạo một mutable list từ danh sách giả lập
//                }
//            }
//            CartScreen(
//                navController = navController,
//                cartItems = cartItems,
//                onBack = { navController.popBackStack() },
//                onIncrease = { it.quantity++ },
//                onDecrease = { if (it.quantity > 1) it.quantity-- },
//                onRemove = { cartItems.remove(it) },
//            )
//        }
        composable("profile") {
            ProfileScreen(
                onEditProfile = { /* TODO */ },
                onViewHistory = { navController.navigate("order_history") },
                onLogout = { navController.navigate("login") { popUpTo("welcome") { inclusive = true } } }
            )
        }
//        composable("order_history") {
//            OrderHistoryScreen(
//                onBack = { navController.popBackStack() },
//                onOrderClick = { order ->
//                    navController.navigate("order_detail/${order.id}")
//                }
//            )
//        }
//        composable(
//            route = "order_detail/{orderId}",
//            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val orderId = backStackEntry.arguments?.getString("orderId")
//            val order = fakeOrderHistory.find { it.id == orderId }
//
//            if (order != null) {
//                OrderDetailScreen(
//                    order = order,
//                    onBack = { navController.popBackStack() }
//                )
//            } else {
//                Text("Đơn hàng không tồn tại.")
//            }
//        }
//        composable(route = "product_list") {
//            ProductListScreen(
//                navController = navController,
//                onBack = { navController.popBackStack() },
//                onCartClick = { navController.navigate("cart") }
//            ) // Sử dụng HomeScreen để hiển thị danh sách sản phẩm
//        }
    }
}