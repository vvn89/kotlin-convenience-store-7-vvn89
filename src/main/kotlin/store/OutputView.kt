package store

import java.text.DecimalFormat

class OutputView {
    val dec = DecimalFormat("#,###")

    fun printProducts(names: MutableList<String>, products: Map<String, Product>) {
        println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n")
        names.forEach {
            var productInfo = ""
            if (products.getValue(it).getPromotionEvent() != null) {
                productInfo = "- $it ${dec.format(products.getValue(it).getPromotionPrice()!!.toInt())}원"
                if (products.getValue(it).getPromotionQuantity() == "0") {
                    productInfo += " 재고 없음"
                } else {
                    productInfo += " ${products.getValue(it).getPromotionQuantity()}개"
                }
                productInfo += " ${products.getValue(it).getPromotionEvent()}"
                productInfo += "\n"
                print(productInfo)
            }
            productInfo = ""
            if (products.getValue(it).getPrice() != null) {
                productInfo = "- $it ${dec.format(products.getValue(it).getPrice()!!.toInt())}원"
                if (products.getValue(it).getQuantity() == "0") {
                    productInfo += " 재고 없음"
                } else {
                    productInfo += " ${products.getValue(it).getQuantity()}개"
                }
                productInfo += "\n"
                print(productInfo)
            }
            productInfo = ""
            if (products.getValue(it).getPrice() == null && products.getValue(it).getPromotionEvent() != null) {
                productInfo = "- $it ${dec.format(products.getValue(it).getPromotionPrice()!!.toInt())}원 재고 없음\n"
                print(productInfo)
            }
        }
        print("\n")
    }

    fun printReceipt() {

    }
}