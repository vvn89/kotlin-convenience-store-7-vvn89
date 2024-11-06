package store

import java.io.File

class ConvenienceStore {
    private val inputView = InputView()
    private val outputView = OutputView()

    private var products: MutableList<Product> = mutableListOf()

    fun start() {
        readProductsFile()
        outputView.printProducts(products)
        val items = inputView.readItem()
        addCart(items)
    }

    fun readProductsFile() {
        val path = "src/main/resources/products.md"
        val productsFileContent = File(path).readLines().drop(1)
        var previousList: MutableMap<String, Int> = mutableMapOf()
        var previous = ""
        var productName = ""
        productsFileContent.forEach {
            val productInfo = it.split(",")
            val name = productInfo[0]
            if (previousList.containsKey(name)) {
                previousList[name] = previousList[name]!! + 1
            }
            if (!previousList.containsKey(name)) {
                previousList[name] = 1
            }
            if (!previous.contains(name) && previousList.containsKey(productName) && previous != "") {
                val pre = previous.split(",")
                if (previousList.getValue(productName) == 1 && pre[3] != "null") {
                    createProduct(listOf(pre[0], pre[1], "재고 없음", "null"))
                }
            }
            createProduct(productInfo)
            previous = it
            productName = productInfo[0]
        }
    }

    private fun createProduct(productInfo: List<String>) {
        var promotion: String? = null
        if (productInfo[3] != "null") {
            promotion = productInfo[3]
        }
        products.add(
            Product(
                name = productInfo[0],
                price = productInfo[1].toInt(),
                quantity = productInfo[2],
                promotion = promotion,
            )
        )
    }

    fun addCart(items: String): List<Cart> {
        val cartList: MutableList<Cart> = mutableListOf()
        items.split(",").forEach {
            val item = it.split("-")
            cartList.add(
                Cart(
                    item[0].replace("[", ""),
                    item[1].replace("]", "")
                )
            )
        }
        return cartList
    }
}