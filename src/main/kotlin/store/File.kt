package store

import java.io.File

class File {
    private val product: MutableList<Product> = mutableListOf()
    private val promotions: MutableList<Promotion> = mutableListOf()

    private fun read(path: String): List<String> {
        return File(path).readLines().drop(1)
    }

    fun readProductsFile(): MutableList<Product> {
        val productsFileContent = File().read(PRODUCT_FILE_PATH)
        productsFileContent.forEach {
            createProduct(it)
        }
        return product
    }

    private fun createProduct(productInformation: String) {
        val productInfo = productInformation.split(SEPARATOR)
        val alreadyExist = product.find { it.name == productInfo[0] }
        when {
            alreadyExist == null && productInfo[3] == NULL_STRING -> addRegularProduct(productInfo)
            alreadyExist == null && productInfo[3] != NULL_STRING -> addPromotionProduct(productInfo)
            alreadyExist != null -> {
                if (productInfo[3] == NULL_STRING) {
                    alreadyExist.quantity = productInfo[2].toInt()
                }
            }
        }
    }

    private fun addPromotionProduct(productInfo: List<String>) {
        product.add(
            Product(
                name = productInfo[0],
                price = productInfo[1].toInt(),
                promotionQuantity = productInfo[2].toInt(),
                promotionName = productInfo[3]
            )
        )
    }

    private fun addRegularProduct(productInfo: List<String>) {
        product.add(
            Product(
                name = productInfo[0],
                price = productInfo[1].toInt(),
                quantity = productInfo[2].toInt()
            )
        )
    }

    fun readPromotionsFile(): List<Promotion> {
        val promotionsFileContent = File().read(PROMOTION_FILE_PATH)
        promotionsFileContent.forEach {
            createPromotionInfo(it)
        }
        return promotions
    }

    private fun createPromotionInfo(event: String) {
        val promotionInfo = event.split(SEPARATOR)
        promotions.add(
            Promotion(
                name = promotionInfo[0],
                buy = promotionInfo[1].toInt(),
                get = promotionInfo[2].toInt(),
                stareDate = promotionInfo[3],
                endDate = promotionInfo[4]
            )
        )
    }

    companion object {
        const val SEPARATOR = ","
        const val NULL_STRING = "null"
        const val PRODUCT_FILE_PATH = "src/main/resources/products.md"
        const val PROMOTION_FILE_PATH = "src/main/resources/promotions.md"
    }
}