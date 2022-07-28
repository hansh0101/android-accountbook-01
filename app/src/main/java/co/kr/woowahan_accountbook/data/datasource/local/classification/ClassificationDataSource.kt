package co.kr.woowahan_accountbook.data.datasource.local.classification

import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto

interface ClassificationDataSource {
    fun getClassification(id: Int): ClassificationDto
    fun getClassifications(): List<ClassificationDto>
    fun getClassificationsByType(isIncome: Boolean): List<ClassificationDto>
    fun insertClassification(type: String, color: String, isIncome: Int)
    fun updateClassification(id: Int, type: String, color: String, isIncome: Int)
}