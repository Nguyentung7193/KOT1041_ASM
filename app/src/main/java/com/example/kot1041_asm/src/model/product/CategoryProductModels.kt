data class ProductResponse(
    val code: Int,
    val msg: String,
    val data: List<Product>
)

data class Product(
    val _id: String,
    val name: String,
    val price: Double,
    val image: String,
    val category: String // Nếu API trả về object category thì đổi thành Category
)

data class CategoryResponse(
    val code: Int,
    val msg: String,
    val data: List<Category>
)

data class Category(
    val _id: String,
    val name: String,
    val description: String,
    val createdAt: String,
    val __v: Int
)
data class ProductDetailResponse(
    val code: Int,
    val msg: String,
    val data: ProductDetail
)

data class ProductDetail(
    val _id: String,
    val name: String,
    val type: String,
    val price: Double,
    val description: String,
    val __v: Int,
    val createdAt: String,
    val updatedAt: String
)

