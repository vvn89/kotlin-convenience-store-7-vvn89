package store

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReceiptTest {
    @Test
    fun `영수증 출력 검증`() {
        // given
        val expectedResult = "===========W 편의점=============\n" +
                "상품명\t\t수량\t금액\n" +
                "에너지바 \t\t5 \t10,000\n" +
                "==============================\n" +
                "총구매액\t\t5\t10,000\n" +
                "내실돈\t\t\t 10,000"
        val input: List<Cart> = listOf(Cart("에너지바", "5"))

        // when
        val result = Receipt().creatReceipt(input)

        // then
        assertThat(result).isEqualTo(expectedResult)
    }
}