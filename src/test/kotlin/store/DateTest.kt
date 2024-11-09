package store

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DateTest {
    @Test
    fun `프로모션 기간 안에 있는지 검증`() {
        // given
        val expectedResult = true
        val input = "탄산2+1"

        // when
        val result = Date().isContainPromotion(input)

        // then
        assertThat(result).isEqualTo(expectedResult)
    }
}