package store.view

import store.Product
import store.Receipt
import java.text.DecimalFormat

class OutputView {
    val dec = DecimalFormat("#,###")

    fun printProducts(names: MutableList<String>, products: Map<String, Product>) {
        println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n")
        names.forEach {
            var productInfo = ""
            if (products.getValue(it).promotionEvent != null) {
                productInfo = "- $it ${dec.format(products.getValue(it).price)}원"
                if (products.getValue(it).promotionQuantity == 0) {
                    productInfo += " 재고 없음"
                } else {
                    productInfo += " ${products.getValue(it).promotionQuantity}개"
                }
                productInfo += " ${products.getValue(it).promotionEvent}"
                productInfo += "\n"
                print(productInfo)
            }
            productInfo = ""
            if (products.getValue(it).quantity != null) {
                productInfo = "- $it ${dec.format(products.getValue(it).price)}원"
                if (products.getValue(it).quantity == 0) {
                    productInfo += " 재고 없음"
                } else {
                    productInfo += " ${products.getValue(it).quantity}개"
                }
                productInfo += "\n"
                print(productInfo)
            }
            productInfo = ""
            if (products.getValue(it).quantity == null && products.getValue(it).promotionEvent != null) {
                productInfo = "- $it ${dec.format(products.getValue(it).price)}원 재고 없음\n"
                print(productInfo)
            }
        }
        print("\n")
    }

    fun printReceipt(receipt: String) {
        println(receipt)
    }
}