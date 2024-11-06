package store

import java.io.File

class ConvenienceStore {
    private val inputView = InputView()
    private val outputView = OutputView()

    private var products: MutableList<Product> = mutableListOf()

    init {
        outputView.printIntroduce()
        readProductsFile()
    }

    fun start() {
        outputView.printProducts(products)
        inputView.readItem()
    }

    fun readProductsFile() {
        val path = "src/main/resources/products.md"
        val productsFileContent = File(path).readLines().drop(1).dropLast(1)
        productsFileContent.forEach { createProduct(it) }
    }

    private fun createProduct(product: String) {
        val productInfo = product.split(",")
        var promotion: String? = null
        if (productInfo[3] != "null") {
            promotion = productInfo[3]
        }
        products.add(Product(
            name = productInfo[0],
            price = productInfo[1].toInt(),
            quantity = productInfo[2],
            promotion = promotion)
        )
    }
}