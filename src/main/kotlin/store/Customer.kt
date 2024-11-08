package store

const val MAX_BENEFIT_PRICE = 8_000

data class Customer(
    var cartItems: List<Cart> = listOf(),
    var shoppingItems: MutableList<Cart> = mutableListOf(),
    var totalPrice: Int= 0,
    var totalCount: Int = 0,
    var nameEvent: String = "",
    var quantityEvent: Int = 0,
    var discountEvent: Int = 0,
    var discountMembership: Int = 0,
    var finalMoney: Int = 0
) {
    fun discountPriceMembership() {
        val benefitPrice = (totalPrice - discountEvent) / 10 * 3
        if (benefitPrice > MAX_BENEFIT_PRICE) {
            discountMembership = MAX_BENEFIT_PRICE
        }
        discountMembership = benefitPrice
    }

    fun calculateFinalMoney() {
        finalMoney = totalPrice - discountEvent - discountMembership
    }
}