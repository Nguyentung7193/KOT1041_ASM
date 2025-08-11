package com.example.kot1041_asm.src.ui.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kot1041_asm.R
import com.example.kot1041_asm.src.DataStore.auth.TokenManager
import com.example.kot1041_asm.src.viewmodle.auth.LoginState
import com.example.kot1041_asm.src.viewmodle.auth.LoginViewModel

@Composable
fun LoginScreen(navController: NavController,  viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.state.collectAsState()
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                val token = (loginState as LoginState.Success).data.token
                if (!token.isNullOrEmpty()) {
                    tokenManager.saveToken(token)
                }
                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0D0D0D), Color(0xFF1F0036))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Đăng nhập",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE1BEE7) // tím nhẹ
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFBA68C8),
                    focusedLabelColor = Color(0xFFCE93D8),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFFD1C4E9)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mật khẩu") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFBA68C8),
                    focusedLabelColor = Color(0xFFCE93D8),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFFD1C4E9)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.login(email, password)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9C27B0) // tím chính
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (loginState is LoginState.Loading) {
                    CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(20.dp))
                } else {
                    Text("Đăng nhập", color = Color.White)
                }
            }
            if (loginState is LoginState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = Color.Red,
                    fontSize = 14.sp
                )
                Log.d("LoginScreen", "Error: ${(loginState as LoginState.Error).message}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {  navController.navigate("register") }) {
                Text("Chưa có tài khoản? Đăng ký", color = Color(0xFFCE93D8))
            }
        }
    }
}
