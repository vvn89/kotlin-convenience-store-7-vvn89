package store

class Membership {
    fun getBenefitPrice(price: Int): Int {
        val benefitPrice = price / 10 * 3
        if (benefitPrice > MAX_BENEFIT_PRICE) {
            return MAX_BENEFIT_PRICE
        }
        return benefitPrice
    }

    companion object {
        const val MAX_BENEFIT_PRICE = 8_000
    }
}