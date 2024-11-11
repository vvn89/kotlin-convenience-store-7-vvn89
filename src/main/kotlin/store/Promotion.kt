package store

import camp.nextstep.edu.missionutils.DateTimes

data class Promotion(
    val name: String,
    val buy: Int,
    val get: Int,
    val stareDate: String,
    val endDate: String,
    private var bundle: Int = 0,
) {
    init {
        bundle = buy + get
    }

    fun getBundle(): Int {
        return bundle
    }

    fun isContainPeriod(): Boolean {
        val today = DateTimes.now().toString().split(TIME_SEPARATOR)[0]
        val startDate = stareDate
        val endDate = endDate
        return today in startDate..endDate
    }

    companion object {
        const val TIME_SEPARATOR = "T"
    }
}