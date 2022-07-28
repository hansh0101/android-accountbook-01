package co.kr.woowahan_accountbook.domain.entity.dto

data class ClassificationDto(
    val id: Int,
    val classificationType: String,
    val classificationColor: String,
    val isIncome: Boolean
)
