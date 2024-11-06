package store

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class ConvenienceStoreTest {
    // addCart 메서드 테스트 작성
    @Test
    fun `입력을 장바구니에 추가하는 함수 검증`() {
        // given
        val input = "[사이다-2],[감자칩-1]"
        val result: List<Cart> = listOf(
            Cart("사이다", "2"),
            Cart("감자칩", "1"),
        )

        // when
        val carts: List<Cart> = ConvenienceStore().addCart(input)

        // then
        assertThat(carts).isEqualTo(result)
    }
}