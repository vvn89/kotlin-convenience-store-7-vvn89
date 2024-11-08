package store

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
)