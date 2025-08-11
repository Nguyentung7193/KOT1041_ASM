import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kot1041_asm.src.model.cart.CartItem
import androidx.compose.ui.res.painterResource


@Composable
fun CartItemComposable(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (cartItem.product != null) {
            // Nếu có ảnh URL bạn có thể thay thế "imageUrl" bằng trường ảnh thật
            AsyncImage(
                model = cartItem.product.image ?: "",
                contentDescription = cartItem.product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_menu_report_image),
                placeholder = painterResource(id = R.drawable.ic_menu_gallery)
            )
        } else {
            // Placeholder nếu product null
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("No Image", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cartItem.product?.name ?: "Sản phẩm không tồn tại",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFFD1AFFF)
            )

            Text(
                text = "${cartItem.product?.price ?: 0} đ",
                color = Color.White,
                fontSize = 14.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Default.Remove, contentDescription = "Giảm", tint = Color.White)
                }
                Text(
                    text = "${cartItem.quantity}",
                    color = Color.White,
                    fontSize = 16.sp
                )
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.Add, contentDescription = "Tăng", tint = Color.White)
                }
            }
        }

        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color.Red)
        }
    }
}
