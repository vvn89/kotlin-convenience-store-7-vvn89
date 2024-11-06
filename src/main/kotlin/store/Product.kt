package store

data class Product(
    private var price: String? = null,
    private var quantity: String? = null,
    private var promotionPrice: String? = null,
    private var promotionQuantity: String? = null,
    private var promotionEvent: String? = null,
) {
    fun setPrice(input: String) {
        price = input
    }

    fun setQuantity(input: String) {
        quantity = input
    }

    fun setPromotionPrice(input: String) {
        promotionPrice = input
    }

    fun setPromotionQuantity(input: String) {
        promotionQuantity = input
    }

    fun setPromotionEvent(input: String) {
        promotionEvent = input
    }

    fun getPrice(): String? {
        return price
    }

    fun getQuantity(): String? {
        return quantity
    }

    fun getPromotionPrice(): String? {
        return promotionPrice
    }

    fun getPromotionQuantity(): String? {
        return promotionQuantity
    }

    fun getPromotionEvent(): String? {
        return promotionEvent
    }
}