package co.kr.woowahan_accountbook.data.datasource.local.history

import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto

interface HistoryDataSource {
    fun getHistory(id: Int): HistoryDto
    fun getHistories(
        year: Int,
        month: Int,
        incomeFlag: Boolean,
        expenditureFlag: Boolean
    ): List<HistoryDto>

    fun insertHistory(
        amount: Int,
        description: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int,
        classificationId: Int
    )

    fun updateHistory(
        id: Int,
        amount: Int,
        description: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int,
        classificationId: Int
    )

    fun deleteHistories(ids: List<Int>)
    fun getTotalAmountByType(year: Int, month: Int, isIncome: Boolean): Int
}