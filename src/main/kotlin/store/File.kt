package store

import java.io.File

class File {
    private val product: MutableList<Product> = mutableListOf()
    private val promotions: MutableList<Promotion> = mutableListOf()

    private fun read(path: String): List<String> {
        return File(path).readLines().drop(1)
    }

    fun readProductsFile(): MutableList<Product> {
        val productsFileContent = File().read("src/main/resources/products.md")
        productsFileContent.forEach {
            createProduct(it)
        }
        return product
    }

    private fun createProduct(productInformation: String) {
        val productInfo = productInformation.split(",")
        val alreadyExist = product.find { it.name == productInfo[0] }
        if (alreadyExist == null) {
            if (productInfo[3] == "null") {
                product.add(
                    Product(
                        name = productInfo[0],
                        price = productInfo[1].toInt(),
                        quantity = productInfo[2].toInt()
                    )
                )
            }
            if (productInfo[3] != "null") {
                product.add(
                    Product(
                        name = productInfo[0],
                        price = productInfo[1].toInt(),
                        promotionQuantity = productInfo[2].toInt(),
                        promotionName = productInfo[3]
                    )
                )
            }
        }
        if (alreadyExist != null) {
            if (productInfo[3] == "null") {
                alreadyExist.quantity = productInfo[2].toInt()
            }
        }
    }

    fun readPromotionsFile(): List<Promotion> {
        val promotionsFileContent = File().read("src/main/resources/promotions.md")
        promotionsFileContent.forEach {
            createPromotionInfo(it)
        }
        return promotions
    }

    private fun createPromotionInfo(event: String) {
        val promotionInfo = event.split(",")
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
}