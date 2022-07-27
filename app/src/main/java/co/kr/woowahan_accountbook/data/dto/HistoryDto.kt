package co.kr.woowahan_accountbook.data.dto

data class HistoryDto(
    val id: Int,
    val amount: Int,
    val description: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val paymentId: Int,
    val classificationId: Int
)
