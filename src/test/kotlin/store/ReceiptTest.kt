//package store
//
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//
//class ReceiptTest {
//    @Test
//    fun `영수증 출력 검증`() {
//        // given
//        val expectedResult = "===========W 편의점=============\n" +
//                "상품명\t\t수량\t금액\n" +
//                "에너지바 \t\t5 \t10,000\n" +
//                "==============================\n" +
//                "총구매액\t\t5\t10,000\n" +
//                "멤버쉽할인\t\t\t-0\n" + "내실돈\t\t\t 10,000"
//
//        val customer = Customer()
//        val input: List<Cart> = listOf(Cart("에너지바", 5, 2000))
//
//        // when
//        val result = Receipt().creatReceipt(input, customer)
//
//        // then
//        assertThat(result).isEqualTo(expectedResult)
//    }
//
//    @Test
//    fun `영수증 멤버쉽 할인 금액 있을 때`() {
//        // given
//        val expectedResult = "===========W 편의점=============\n" +
//                "상품명\t\t수량\t금액\n" +
//                "에너지바 \t\t5 \t10,000\n" +
//                "==============================\n" +
//                "총구매액\t\t0\t0\n" +
//                "멤버쉽할인\t\t\t-1,000\n" + "내실돈\t\t\t 0"
//
//        val customer = Customer(discountMembershipPrice = 1000)
//        val input: List<Cart> = listOf(Cart("에너지바", 5, 2000))
//
//        // when
//        val result = Receipt().creatReceipt(input, customer)
//
//        // then
//        assertThat(result).isEqualTo(expectedResult)
//    }
//
//    @Test
//    fun `영수증 행사할인 금액 있을 때`() {
//        // given
//        val expectedResult = "===========W 편의점=============\n" +
//                "상품명\t\t수량\t금액\n" +
//                "에너지바 \t\t5 \t10,000\n" +
//                "=============증\t정===============\n" +
//                "\t\t0\n" +
//                "==============================\n" +
//                "총구매액\t\t0\t0\n" +
//                "행사할인\t\t\t-1,000\n" +
//                "멤버쉽할인\t\t\t-0\n" + "내실돈\t\t\t 0"
//
//        val customer = Customer(discountPromotionPrice = 1000)
//        val input: List<Cart> = listOf(Cart("에너지바", 5, 2000))
//
//        // when
//        val result = Receipt().creatReceipt(input, customer)
//
//        // then
//        assertThat(result).isEqualTo(expectedResult)
//    }
//}