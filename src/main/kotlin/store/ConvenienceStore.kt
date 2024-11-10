package store

import store.view.InputView
import store.view.OutputView

class ConvenienceStore {
    private val inputView = InputView()
    private val outputView = OutputView()

    private var products: List<Product> = listOf()
    private var promotions: List<Promotion> = listOf()
    private var customer = Customer()

    init {
        products = File().readProductsFile()
        promotions = File().readPromotionsFile()
    }

    fun start() {
        val items = getItem()
        customer.cartItems = addCart(items)
        buyItems(customer.cartItems)
        val receipt = Receipt(customer)
        val isMembership = inputView.readMembership()
        if (isMembership == "Y") {
            receipt.setMembershipDiscountPrice()
        }
        outputView.printReceipt(receipt)
        val anythingElse = inputView.readAnythingElse()
        if (anythingElse == "Y") {
            restart()
        }
    }

    private fun restart() {
        customer = Customer()
        start()
    }

    private fun getItem(): String {
        outputView.printProducts(products)
        return inputView.readItem()
    }

    private fun findPromotion(item: Product): Promotion? {
        return promotions.find { it.name == item.name }
    }

    private fun findProduct(item: Product): Product {
        return products.find { it.name == item.name }!!
    }

    private fun findProductWithName(name: String): Product {
        return products.find { it.name == name }!!
    }

    fun buyItems(cart: List<Product>) {
        cart.forEach { item ->
            if (item.quantity > findProduct(item).getTotalQuantity()) {
                outputView.printError("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.")
                start()
            }
            if (findPromotion(item) != null && findPromotion(item)!!.isContainPeriod()) {
                buyPromotionProduct(item)
            }
            buyNormalProduct(item)
        }
    }

    private fun buyNormalProduct(item: Product) {
        findProduct(item).quantity -= item.quantity
        val purchasedProducts =
            Product(name = item.name, price = item.price, quantity = item.quantity)
        customer.shoppingItems.add(purchasedProducts)
        customer.shoppingMembershipPrice.add(item.price * item.quantity)
    }

    private fun buyPromotionProduct(item: Product) {
        val bundle = findPromotion(item)!!.getBundle()
        val notBundleCount = item.quantity % bundle
        if (findProduct(item).promotionQuantity % bundle == 0) {
            if (item.quantity <= findProduct(item).promotionQuantity) {
                when {
                    notBundleCount == 0 -> buyPromotionProductWithBundle(item, 0)
                    notBundleCount == 1 && bundle == 3 -> buyPromotionProductWithBundle(
                        item,
                        notBundleCount
                    )
                    else -> checkFreeProduct(item)
                }
            } else {
                val regularCount = item.quantity - findProduct(item).promotionQuantity
                val isFullPrice = inputView.readFullPrice(item.name, regularCount)
                when (isFullPrice) {
                    "Y" -> buyPromotionProductWithBundle(item, regularCount)
                    "N" -> buyOnlyPromotionProduct(item, regularCount)
                }
                buyPromotionProductWithRegular(item, regularCount)
            }
        }
    }

    private fun buyOnlyPromotionProduct(item: Product, normal: Int) {
        findProduct(item).promotionQuantity -= (item.quantity - normal)
        val purchasedProducts =
            Product(name = item.name, price = item.price, quantity = (item.quantity - normal))
        customer.shoppingItems.add(purchasedProducts)
        customer.shoppingPromotionItems.add(
            Product(
                name = item.name,
                price = item.price,
                quantity = (item.quantity - normal) / findPromotion(item)!!.getBundle()
            )
        )
        item.quantity = 0 + normal
    }

    private fun buyPromotionProductWithRegular(item: Product, regularCount: Int) {
        findProduct(item).promotionQuantity -= (item.quantity - regularCount)
        val purchasedProducts =
            Product(name = item.name, price = item.price, quantity = (item.quantity - regularCount))
        customer.shoppingItems.add(purchasedProducts)
        customer.shoppingPromotionItems.add(
            Product(
                name = item.name,
                price = item.price,
                quantity = (item.quantity - regularCount) / findPromotion(item)!!.getBundle()
            )
        )
        item.quantity = 0 + regularCount
    }

    private fun buyPromotionProductWithBundle(item: Product, normal: Int) {
        findProduct(item).promotionQuantity -= (item.quantity - normal)
        val purchasedProducts =
            Product(name = item.name, price = item.price, quantity = (item.quantity - normal))
        customer.shoppingItems.add(purchasedProducts)
        customer.shoppingPromotionItems.add(
            Product(
                name = item.name,
                price = item.price,
                quantity = (item.quantity - normal) / findPromotion(item)!!.getBundle()
            )
        )
        item.quantity = 0 + normal
    }

    private fun checkFreeProduct(item: Product) {
        do {
            val checkFree: String = inputView.readBOGO(item.name)
            var again = false
            when (checkFree) {
                "Y" -> buyWithFreeProduct(item)
                "N" -> buyWithoutFreeProduct(item)
                else -> again = true
            }
        } while (again)
    }

    private fun buyWithFreeProduct(item: Product) {
        findProduct(item).promotionQuantity -= (item.quantity + 1)
        val purchasedProducts =
            Product(name = item.name, price = item.price, quantity = (item.quantity + 1))
        customer.shoppingItems.add(purchasedProducts)
        customer.shoppingPromotionItems.add(
            Product(
                name = item.name,
                price = item.price,
                quantity = (item.quantity + 1) / findPromotion(item)!!.getBundle()
            )
        )
        item.quantity = 0
    }

    private fun buyWithoutFreeProduct(item: Product) {
        findProduct(item).promotionQuantity -= (item.quantity)
        val purchasedProducts =
            Product(name = item.name, price = item.price, quantity = (item.quantity))
        customer.shoppingItems.add(purchasedProducts)
        customer.shoppingPromotionItems.add(
            Product(
                name = item.name,
                price = item.price,
                quantity = (item.quantity) / findPromotion(item)!!.getBundle()
            )
        )
        item.quantity = 0
    }

    private fun addCart(itemsInput: String): List<Product> {
        val cartList: MutableList<Product> = mutableListOf()
        itemsInput.split(",").forEach {
            val item = it.split("-")
            val itemName = item[0].replace("[", "")
            val itemQuantity = item[1].replace("]", "").toInt()
            cartList.add(
                Product(
                    name = itemName,
                    quantity = itemQuantity,
                    price = findProductWithName(itemName).price
                )
            )
        }
        return cartList
    }
}