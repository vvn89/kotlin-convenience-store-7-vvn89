package store

import java.text.DecimalFormat

class Receipt {
    val dec = DecimalFormat("#,###")

    fun creatReceipt(productInfo: List<Cart>, customer: Customer): String {
        var receipt = "===========W 편의점=============\n" + "상품명\t\t수량\t금액\n"
        productInfo.forEach {
            val totalPrice: Int = it.price * it.quantity
            receipt += "${it.name} \t\t${it.quantity} \t${dec.format(totalPrice)}\n"
        }
        if (customer.discountEvent != 0) {
            receipt += "=============증\t정===============\n" +
                    "${customer.nameEvent}\t\t${customer.quantityEvent}\n"
        }
        receipt += "==============================\n" +
                "총구매액\t\t${dec.format(customer.totalCount)}\t${dec.format(customer.totalPrice)}\n"
        if (customer.discountEvent != 0) {
            receipt += "행사할인\t\t\t-${dec.format(customer.discountEvent)}\n"
        }
        receipt += "멤버쉽할인\t\t\t-${dec.format(customer.discountMembership)}\n" + "내실돈\t\t\t ${dec.format(customer.finalMoney)}"

        return receipt
    }
}