package store

data class Product(
    val name: String,
    val price: Int,
    var quantity: String,
    var promotion: String? = null,
) {
//    fun reduceQuantity(count: Int) {
//        quantity -= count
//    }
}