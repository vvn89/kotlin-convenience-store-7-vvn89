package store.view

import store.Product
import store.Receipt
import java.text.DecimalFormat

class OutputView {
    val dec = DecimalFormat("#,###")

    fun printProducts(products: List<Product>) {
        println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n")
        products.forEach {
            var productInfo = ""
            if (it.promotionName != null) {
                productInfo = "- ${it.name} ${dec.format(it.price)}원"
                if (it.promotionQuantity == 0) {
                    productInfo += " 재고 없음"
                } else {
                    productInfo += " ${it.promotionQuantity}개"
                }
                productInfo += " ${it.promotionName}"
                productInfo += "\n"
                print(productInfo)
            }
            productInfo = "- ${it.name} ${dec.format(it.price)}원"
            if (it.quantity == 0) {
                productInfo += " 재고 없음"
            } else {
                productInfo += " ${it.quantity}개"
            }
            productInfo += "\n"
            print(productInfo)
//            if (it.quantity == 0 && it.promotionName != null) {
//                productInfo = "- ${it.name} ${dec.format(it.price)}원 재고 없음\n"
//                print(productInfo)
//            }
        }
        print("\n")
    }

    fun printReceipt(receipt: Receipt) {
        println("==============W 편의점================")
        println("상품명\t\t수량\t금액")
        receipt.customerInfo.shoppingItems.forEach {
            if (receipt.customerInfo.summationQuantity[it.name] != 0) {
                val quantity = receipt.customerInfo.summationQuantity[it.name]
                val price: Int = it.price * quantity!!
                println("${it.name} \t\t${quantity} \t${dec.format(price)}")
                receipt.customerInfo.summationQuantity[it.name] = 0
            }
        }
        if (receipt.promotionDiscountCost != 0) {
            println("=============증\t\t정===============")
            receipt.customerInfo.shoppingPromotionItems.forEach {
                if (it.quantity != 0) {
                    println("${it.name}\t\t${it.quantity}")
                }
            }
        }
        println("====================================")
        println("총구매액\t\t${dec.format(receipt.totalProductCount)}\t${dec.format(receipt.totalFirstCost)}")
        if (receipt.promotionDiscountCost != 0) {
            println("행사할인\t\t\t-${dec.format(receipt.promotionDiscountCost)}")
        }
        println("멤버쉽할인\t\t\t-${dec.format(receipt.membershipDiscountCost)}")
        println("내실돈\t\t\t ${dec.format(receipt.totalFinalCost)}")
    }

    fun printError(message: String) {
        println(message)
    }
}