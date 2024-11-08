package store

data class Product(
    var price: Int = 0,
    var quantity: Int = 0,
    var promotionQuantity: Int = 0,
    var promotionEvent: String? = null,
) {
    fun getTotalQuantity(): Int {
        return quantity + promotionQuantity
    }
//    fun setPrice(input: Int) {
//        price = input
//    }
//
//    fun setQuantity(input: Int) {
//        quantity = input
//    }
//
//    fun setPromotionQuantity(input: Int) {
//        promotionQuantity = input
//    }
//
//    fun setPromotionEvent(input: String) {
//        promotionEvent = input
//    }
//    fun getPrice(): Int? {
//        return price
//    }
//
//    fun getQuantity(): Int? {
//        return quantity
//    }
//
//    fun getPromotionQuantity(): Int? {
//        return promotionQuantity
//    }
//
//    fun getPromotionEvent(): String? {
//        return promotionEvent
//    }
//
//    fun deductQuantity(input: Int) {
//        quantity = quantity!!.minus(input)
//    }
//
//    fun deductPromotionQuantity(input: Int) {
//        promotionQuantity = promotionQuantity!!.minus(input)
//    }
}