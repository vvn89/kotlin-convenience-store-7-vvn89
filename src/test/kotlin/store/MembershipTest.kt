package store

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.`in`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MembershipTest {
    @Test
    fun `멤버쉽 할인 금액 검증 - 최대 할인 한도 초과`() {
        // given
        val expectedResult = 8000
        val input = 30000

        // when
        val result = Membership().getBenefitPrice(input)

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `멤버쉽 할인 금액 검증 - normal`() {
        // given
        val expectedResult = 900
        val input = 3000

        // when
        val result = Membership().getBenefitPrice(input)

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `멤버쉽 할인 금액 검증 - 0`() {
        // given
        val expectedResult = 0
        val input = 0

        // when
        val result = Membership().getBenefitPrice(input)

        // then
        assertThat(result).isEqualTo(expectedResult)
    }
}