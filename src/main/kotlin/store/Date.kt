package store

import java.time.LocalDateTime

class Date {
    val promotions: MutableList<Promotion> = mutableListOf()

    init {
        readPromotionsFile()
    }

    fun isContainPromotion(promotionName: String): Boolean {
        promotions.forEach {
            if (it.name == promotionName) {
                now() in it.stareDate .. it.endDate
                return true
            }
        }
        return false
    }

    private fun now(): String {
        return LocalDateTime.now().toString()
    }

    private fun readPromotionsFile() {
        val promotionsFileContent = File().read("src/main/resources/promotions.md")
        promotionsFileContent.forEach {
            createPromotionInfo(it)
        }
    }

    private fun createPromotionInfo(event: String) {
        val promotion = Promotion()
        event.split(",").forEachIndexed { index, s ->
            when(index) {
                0 -> promotion.name = s
                1 -> promotion.buy = s
                2 -> promotion.get = s
                3 -> promotion.stareDate = s
                4 -> promotion.endDate = s
            }
        }
        promotions += promotion
    }
}