package co.kr.woowahan_accountbook.domain.entity.history

import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto

data class HistoryItem(
    val type: Int,
    val id: Int,
    val amount: Int,
    val description: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val paymentId: Int,
    val paymentName: String,
    val classificationId: Int,
    val classificationType: String,
    val classificationColor: String,
    val isIncome: Boolean
) {
    constructor(type: Int, year: Int, month: Int, day: Int) : this(
        type, 0, 0, "", year, month, day, 0, "", 0, "", "", false
    )

    constructor(type: Int, historyDto: HistoryDto) : this(
        type,
        historyDto.id,
        historyDto.amount,
        historyDto.description,
        historyDto.year,
        historyDto.month,
        historyDto.day,
        historyDto.paymentId,
        historyDto.paymentName,
        historyDto.classificationId,
        historyDto.classificationType,
        historyDto.classificationColor,
        historyDto.isIncome
    )

    companion object Type {
        const val HEADER = 0
        const val BODY = 1
    }
}
