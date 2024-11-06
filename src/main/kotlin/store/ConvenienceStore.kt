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
        val items = inputView.readItem()
        addCart(items)
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

    fun addCart(items: String): List<Cart> {
        val cartList: MutableList<Cart> = mutableListOf()
        items.split(",").forEach {
            val item = it.split("-")
            cartList.add(Cart(item[0].replace("[", ""),
                item[1].replace("]", "")))
        }
        return cartList
    }
}