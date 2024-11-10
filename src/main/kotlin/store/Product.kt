package store

data class Product(
    val name: String,
    var price: Int = 0,
    var quantity: Int = 0,
    var promotionQuantity: Int = 0,
    var promotionName: String? = null,
) {
    fun getTotalQuantity(): Int {
        return quantity + promotionQuantity
    }
}