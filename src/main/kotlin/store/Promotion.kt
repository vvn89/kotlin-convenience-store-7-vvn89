package store

import java.time.LocalDate

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
        val today = LocalDate.now()
        val startDate = LocalDate.parse(stareDate)
        val endDate = LocalDate.parse(endDate)
        return today in startDate .. endDate
    }
}