package store

import store.view.InputView
import store.view.OutputView
import java.io.File

class ConvenienceStore {
    private val inputView = InputView()
    private val outputView = OutputView()

    private var productNames: MutableList<String> = mutableListOf()
    private var products: MutableMap<String, Product> = mutableMapOf()
    var customer = Customer()

    init {
        readProductsFile()
    }

    fun start() {
        outputView.printProducts(productNames, products)
        val items = inputView.readItem()
        customer.cartItems = addCart(items)
        purchaseItems(customer.cartItems)
        customer.totalPrice = products.values.sumOf { it.price * it.quantity }
        Receipt().creatReceipt(customer.shoppingItems, customer)
    }

    fun purchaseItems(cart: List<Cart>) {
        cart.forEach {
            reducePromotionQauntity(it)
        }
    }

    private fun reducePromotionQauntity(item: Cart) {
        var unappliedQuantity = 0
        var finalQuantity = 0
        if (products.getValue(item.name).promotionQuantity != 0) {
            if (item.quantity > products.getValue(item.name).promotionQuantity) {
                unappliedQuantity = item.quantity - products.getValue(item.name).promotionQuantity
                val isFullPrice = inputView.readFullPrice(item.name, unappliedQuantity)
                if (isFullPrice == "Y") {
                    finalQuantity = item.quantity - unappliedQuantity
                }
                if (isFullPrice == "N") {
                    finalQuantity
                }
            }
        }
    }

//    private fun isDeficient(item: Cart): Boolean { }

    private fun readProductsFile() {
        val path = "src/main/resources/products.md"
        val productsFileContent = File(path).readLines().drop(1)
        productsFileContent.forEach {
            createProduct(it)
        }
    }

    private fun createProduct(productInformation: String) {
        val productInfo = productInformation.split(",")
        val name = productInfo[0]
        if (!productNames.contains(name)) {
            products[name] = Product()
            productNames += name
        }

        if (productInfo[3] == "null") {
            products[name]!!.price = productInfo[1].toInt()
            products[name]!!.quantity = productInfo[2].toInt()
        }

        if (productInfo[3] != "null" ) {
            products[name]!!.price = productInfo[1].toInt()
            products[name]!!.quantity = productInfo[2].toInt()
            products[name]!!.promotionEvent = productInfo[3]
        }

    }

    fun addCart(items: String): List<Cart> {
        val cartList: MutableList<Cart> = mutableListOf()
        items.split(",").forEach {
            val item = it.split("-")
            val itemName = item[0].replace("[", "")
            val itemQuantity = item[0].replace("[", "").toInt()
            cartList.add(
                Cart(
                    itemName,
                    itemQuantity,
                    products.getValue(itemName).price,
                )
            )
        }
        return cartList
    }
}