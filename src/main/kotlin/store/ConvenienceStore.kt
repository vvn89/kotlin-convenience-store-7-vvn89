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
        try {
            val items = getItem()
            customer.cartItems = addCart(items)
        } catch (e: IndexOutOfBoundsException) {
            outputView.printError(ERROR_INVALID_INPUT_FORM_MESSAGE)
            start()
        }
        buyItems(customer.cartItems)
        summary(customer.shoppingItems)
        val receipt = Receipt(customer)
        var isMembership = inputView.readMembership()
        while (true) {
            if (isMembership == YES || isMembership == NO) {
                break
            }
            outputView.printError(ERROR_INVALID_INPUT_MESSAGE)
            isMembership = inputView.readMembership()
        }
        if (isMembership == YES) {
            receipt.setMembershipDiscountPrice()
        }
        outputView.printReceipt(receipt)
        restart()
    }

    private fun summary(shoppingItems: MutableList<Product>) {
        shoppingItems.forEach {
            customer.summationQuantity[it.name] =
                customer.summationQuantity[it.name]?.plus(it.quantity) ?: it.quantity
        }
    }

    private fun restart() {
        val anythingElse = inputView.readAnythingElse()
        if (anythingElse == YES) {
            customer = Customer()
            start()
        }
    }

    private fun getItem(): String {
        outputView.printProducts(products)
        return inputView.readItem()
    }

    private fun findPromotion(item: Product): Promotion? {
        val promotionName = products.find { it.name == item.name }!!.promotionName
        return promotions.find { it.name == promotionName }
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
                outputView.printError(ERROR_INVALID_QUANTITY_MESSAGE)
                restart()
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
            var isFullPrice = inputView.readFullPrice(item.name, regularCount)
            while (true) {
                if (isFullPrice == YES || isFullPrice == NO) {
                    break
                }
                outputView.printError(ERROR_INVALID_INPUT_MESSAGE)
                isFullPrice = inputView.readFullPrice(item.name, regularCount)
            }
            when (isFullPrice) {
                YES -> buyPromotionProductWithBundle(item, regularCount)
                NO -> buyOnlyPromotionProduct(item, regularCount)
            }
            buyPromotionProductWithRegular(item, regularCount)
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
                YES -> buyWithFreeProduct(item)
                NO -> buyWithoutFreeProduct(item)
                else -> {
                    outputView.printError(ERROR_INVALID_INPUT_MESSAGE)
                    again = true
                }
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
        itemsInput.split(SEPARATOR).forEach {
            val item = it.split(CONNECTOR)
            val itemName = item[0].replace(FORM_PREFIX, "")
            val itemQuantity = item[1].replace(FORM_SUFFIX, "").toInt()
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

    companion object {
        const val ERROR_INVALID_INPUT_MESSAGE = "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."
        const val ERROR_INVALID_INPUT_FORM_MESSAGE = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."
        const val ERROR_INVALID_QUANTITY_MESSAGE = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."

        const val SEPARATOR = ","
        const val CONNECTOR = "-"
        const val FORM_PREFIX = "["
        const val FORM_SUFFIX = "]"
        const val YES = "Y"
        const val NO = "N"
    }
}