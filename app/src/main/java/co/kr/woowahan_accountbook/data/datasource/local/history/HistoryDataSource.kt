package co.kr.woowahan_accountbook.data.datasource.local.history

import co.kr.woowahan_accountbook.data.dto.HistoryDto

interface HistoryDataSource {
    fun getHistory(id: Int): HistoryDto
    fun getHistories(): List<HistoryDto>
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
    fun deleteHistory(id: Int)
}