package com.example.kot1041_asm.src.data.product

import com.example.kot1041_asm.R

data class Product(
    val name: String,
    val price: Int,
    val category: String,
    val imageRes: Int,
    val description: String
)

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)

data class OrderItem(
    val name: String,
    val price: Double,
    val quantity: Int
)

data class OrderHistory(
    val id: String,
    val date: String,
    val items: List<OrderItem>,
    val status: String
) {
    val total: Double
        get() = items.sumOf { it.price * it.quantity }
}

object FakeData {
    val categories = listOf("Tất cả", "MondStadt", "Liyue", "Inazuma", "Sumeru","Fontaine","Natlan")

    val products = listOf(
        Product(
            name = "Jean",
            price = 150000,
            category = "MondStadt",
            imageRes = R.drawable.jean,
            description = "Jean đến từ MondStadt."
        ),
        Product(
            name = "Diluc",
            price = 99000,
            category = "MondStadt",
            imageRes = R.drawable.diluc,
            description = "Diluc - Người bảo vệ MondStadt, với sức mạnh lửa mạnh mẽ."
        ),
        Product(
            name = "Zhongli",
            price = 199000,
            category = "Liyue",
            imageRes = R.drawable.zhongli,
            description = "Zhongli - Vị thần của Liyue, với sức mạnh địa chất và lịch sử phong phú."
        ),
        Product(
            name = "Hu Tao",
            price = 220000,
            category = "Liyue",
            imageRes = R.drawable.hutao,
            description = "Hu Tao - Người đứng đầu Nhà tang lễ Liyue, với tính cách vui tươi và bí ẩn."
        ),
        Product(
            name = "Raiden Shogun",
            price = 119000,
            category = "Inazuma",
            imageRes = R.drawable.raiden,
            description = "Raiden Shogun - Lãnh đạo của Inazuma, với sức mạnh điện và sự nghiêm khắc."
        ),
        Product(
            name = "Yae Miko",
            price = 129000,
            category = "Inazuma",
            imageRes = R.drawable.yaemiko,
            description = "Yae Miko - Nữ tu sĩ của Inazuma, với sức mạnh ma thuật và tính cách tinh nghịch."
        ),
        Product(
            name = "Tighnari",
            price = 159000,
            category = "Sumeru",
            imageRes = R.drawable.tighnari,
            description = "Tighnari - Người bảo vệ rừng Sumeru, với sức mạnh thiên nhiên và kiến thức phong phú."
        ),
        Product(
            name = "Alhaitham",
            price = 179000,
            category = "Sumeru",
            imageRes = R.drawable.alhaitham,
            description = "Alhaitham - Nhà nghiên cứu của Sumeru, với trí tuệ sắc bén và khả năng phân tích sâu sắc."
        ),
        Product(
            name = "Neuvillette",
            price = 199000,
            category = "Fontaine",
            imageRes = R.drawable.neuvillette,
            description = "Neuvillette - Người bảo vệ Fontaine, với sức mạnh nước và khả năng điều khiển thủy triều."
        ),
        Product(
            name = "Furina",
            price = 189000,
            category = "Fontaine",
            imageRes = R.drawable.furina,
            description = "Furina - Nữ thần của Fontaine, với sức mạnh nước và sự bí ẩn."
        ),
        Product(
            name = "Mavuika",
            price = 199000,
            category = "Natlan",
            imageRes = R.drawable.mavuika,
            description = "Mavuika - Người bảo vệ Natlan, với sức mạnh lửa và khả năng điều khiển nhiệt độ."
        ),
        Product(
            name = "Kinick",
            price = 179000,
            category = "Natlan",
            imageRes = R.drawable.kinick,
            description = "Kinick - Nhà nghiên cứu của Natlan, với trí tuệ sắc bén và khả năng phân tích sâu sắc."
        )
    )

    val fakeCartItems = listOf(
        CartItem(product = products[0], quantity = 2),
        CartItem(product = products[1], quantity = 1),
        CartItem(product = products[3], quantity = 3),
        CartItem(product = products[5], quantity = 1),
        CartItem(product = products[7], quantity = 2),
        CartItem(product = products[9], quantity = 1)
    )

    val fakeOrderHistory = listOf(
        OrderHistory(
            id = "DH001",
            date = "2025-07-21",
            items = listOf(
                OrderItem(name = products[0].name, price = products[0].price.toDouble(), quantity = 1),
                OrderItem(name = products[2].name, price = products[2].price.toDouble(), quantity = 2)
            ),
            status = "Đang xử lý"
        ),
        OrderHistory(
            id = "DH002",
            date = "2025-07-18",
            items = listOf(
                OrderItem(name = products[1].name, price = products[1].price.toDouble(), quantity = 1),
                OrderItem(name = products[4].name, price = products[4].price.toDouble(), quantity = 2)
            ),
            status = "Đã giao"
        )
    )
}
