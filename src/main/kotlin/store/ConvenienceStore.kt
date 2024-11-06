package store

import java.io.File

class ConvenienceStore {
    private val inputView = InputView()
    private val outputView = OutputView()

    private var productNames: MutableList<String> = mutableListOf()
    private var products: MutableMap<String, Product> = mutableMapOf()

    fun start() {
        readProductsFile()
        outputView.printProducts(productNames, products)
        val items = inputView.readItem()
        val cart = addCart(items)
    }

    private fun readProductsFile() {
        val path = "src/main/resources/products.md"
        val productsFileContent = File(path).readLines().drop(1)
        productsFileContent.forEach {
            createProduct(it)
        }
    }

    private fun createProduct(productInfomation: String) {
        val productInfo = productInfomation.split(",")
        if (!productNames.contains(productInfo[0])) {
            products[productInfo[0]] = Product()
            productNames += productInfo[0]
        }

        if (productInfo[3] == "null") {
            products[productInfo[0]]!!.setPrice(productInfo[1])
            products[productInfo[0]]!!.setQuantity(productInfo[2])
        }

        if (productInfo[3] != "null" ) {
            products[productInfo[0]]!!.setPromotionPrice(productInfo[1])
            products[productInfo[0]]!!.setPromotionQuantity(productInfo[2])
            products[productInfo[0]]!!.setPromotionEvent(productInfo[3])
        }

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