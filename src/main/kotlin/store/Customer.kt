package store

data class Customer(
    var cartItems: List<Product> = listOf(),
    var shoppingItems: MutableList<Product> = mutableListOf(),
    var shoppingPromotionItems: MutableList<Product> = mutableListOf(),
    var shoppingMembershipPrice: MutableList<Int> = mutableListOf(),
    var summationQuantity: MutableMap<String, Int> = mutableMapOf()
)