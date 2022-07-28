package co.kr.woowahan_accountbook.domain.entity.setting

import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto

data class SettingClassification(
    val type: Int,
    val id: Int,
    val classificationType: String,
    val classificationColor: String,
    val isIncome: Boolean
) {
    constructor(type: Int, isIncome: Boolean) : this(type, 0, "", "", isIncome)
    constructor(type: Int, classification: ClassificationDto) : this(
        type,
        classification.id,
        classification.classificationType,
        classification.classificationColor,
        classification.isIncome
    )

    companion object Type {
        const val HEADER = 0
        const val BODY = 1
        const val FOOTER = 2
    }
}