package co.kr.woowahan_accountbook.domain.entity.dto

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
