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
        customer.totalPrice = customer.shoppingItems.sumOf { it.price * it.quantity }
        val isMembership = inputView.readMembership()
        if (isMembership == "Y") {
            customer.discountPriceMembership()
        }
        customer.calculateFinalMoney()
        val receipt = Receipt().creatReceipt(customer.shoppingItems, customer)
        outputView.printReceipt(receipt)

    }

    fun purchaseItems(cart: List<Cart>) {
        cart.forEach {
            if (products.getValue(it.name).getTotalQuantity() < 1) {
                // 올바르지 않은 입력 에러 출력
            }
            reducePromotionQauntity(it)
        }
    }

    private fun reducePromotionQauntity(item: Cart): Boolean {
        var unappliedQuantity = 0
        val productInfo = products.getValue(item.name)
        var possiblePromotion = 0
        var promotionCount = 0
        if (productInfo.promotionQuantity > 0 && productInfo.promotionEvent != null) {
            if (item.quantity < productInfo.promotionQuantity) {
                when(productInfo.promotionEvent) {
                    "탄산2+1" -> {
                        possiblePromotion = item.quantity % 3 - 1
                        promotionCount = 3
                    }
                    "MD추천상품" -> {
                        possiblePromotion = item.quantity % 2
                        promotionCount = 2
                    }
                    "반짝할인" -> {
                        possiblePromotion = item.quantity % 2
                        promotionCount = 2
                    }
                }
                if (possiblePromotion == 1) {
                    val answer = inputView.readBOGO(item.name)
                    if (answer == "Y") {
                        customer.totalCount += item.quantity+1
                        customer.shoppingItems.add(Cart(item.name, item.quantity+1, item.price))
                        customer.discountEvent += ((productInfo.promotionQuantity+1) / promotionCount) * item.price
                        return true
                    }
                    if (answer == "N") {
                        customer.totalCount += item.quantity
                        customer.shoppingItems.add(Cart(item.name, item.quantity, item.price))
                        customer.discountEvent += (productInfo.promotionQuantity / promotionCount) * item.price
                        return true
                    }
                }
            } else {
                unappliedQuantity = item.quantity - productInfo.promotionQuantity
                val isFullPrice = inputView.readFullPrice(item.name, unappliedQuantity)
                if (isFullPrice == "Y") {
                    customer.totalCount += item.quantity
                    customer.shoppingItems.add(Cart(item.name, item.quantity, item.price))
                    return true
                }
                if (isFullPrice == "N") {
                    customer.totalCount += (item.quantity - unappliedQuantity)
                    customer.shoppingItems.add(Cart(item.name, item.quantity-unappliedQuantity, item.price))
                    return true
                }
            }
        }
        if (productInfo.quantity > 0 && productInfo.promotionEvent == null) {
            customer.totalCount += item.quantity
            customer.shoppingItems.add(Cart(item.name, item.quantity, item.price))
            return true
        }
        return false
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
            val itemQuantity = item[1].replace("]", "").toInt()
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