package store

import java.text.DecimalFormat

class OutputView {
    val dec = DecimalFormat("#,###")

    fun printProducts(products: List<Product>) {
        println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n")
        //TODO: 재고 파일에는 없어도, 프로모션 재고만 있는 상품에 대해 일반 상품의 재고 없음을 알려야 함.
        // "파일에 있는 상품 목록 출력" 테스트를 실행하였을 때
        // 현재 ["- 오렌지주스 1,800원 재고 없음", "- 탄산수 1,200원 재고 없음", "- 컵라면 1,700원 10개"] 누락됨.

        products.forEach {
            var productInfo = "- ${it.name} ${dec.format(it.price)}원"
            if (it.quantity == "재고 없음") {
                productInfo += " ${it.quantity}"
            } else {
                productInfo += " ${it.quantity}개"
            }
            if (it.promotion != null) {
                productInfo += " ${it.promotion}"
            }
            productInfo += "\n"
            print(productInfo)
        }
        print("\n")
    }
}