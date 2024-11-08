package store

import camp.nextstep.edu.missionutils.Console

class InputView {
    fun readItem(): String {
        println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])")
        val input = Console.readLine()
        return input
    }

    fun readBOGO(name: String): String {
        println("현재 ${name}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)")
        val input = Console.readLine()
        return input
    }

    fun readFullPrice(name: String, quantity: String): String {
        println("현재 ${name} ${quantity}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)")
        val input = Console.readLine()
        return input
    }

    fun readMembership(): String {
        println("멤버십 할인을 받으시겠습니까? (Y/N)")
        val input = Console.readLine()
        return input
    }

    fun readAnythingElse(): String {
        println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)")
        val input = Console.readLine()
        return input
    }
}