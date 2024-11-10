package store

const val MAX_BENEFIT_PRICE = 8_000

data class Receipt(
    val customerInfo: Customer,
    var totalProductCount: Int = 0,
    var totalFirstCost: Int = 0,
    var promotionDiscountCost: Int = 0,
    var membershipDiscountCost: Int = 0,
    var totalFinalCost: Int = 0
) {
    init {
        setTotalProductCount()
        setTotalFirstCost()
        setPromotiondiscountPrice()
        setTotalFinalCost()
    }

    private fun setTotalProductCount() {
        customerInfo.shoppingItems.forEach {
            totalProductCount += it.quantity
            totalProductCount += it.promotionQuantity
        }
    }

    private fun setTotalFirstCost() {
        customerInfo.shoppingItems.forEach {
            totalFirstCost += it.quantity * it.price
            totalFirstCost += it.promotionQuantity * it.price
        }
    }

    private fun setPromotiondiscountPrice() {
        customerInfo.shoppingPromotionItems.forEach {
            promotionDiscountCost += it.quantity * it.price
        }
    }

    fun setMembershipDiscountPrice() {
        membershipDiscountCost = customerInfo.shoppingMembershipPrice.sum() / 10 * 3
        if (membershipDiscountCost > MAX_BENEFIT_PRICE) {
            membershipDiscountCost = MAX_BENEFIT_PRICE
        }
        setTotalFinalCost()
    }

    fun setTotalFinalCost() {
        totalFinalCost = totalFirstCost - promotionDiscountCost - membershipDiscountCost
    }
}