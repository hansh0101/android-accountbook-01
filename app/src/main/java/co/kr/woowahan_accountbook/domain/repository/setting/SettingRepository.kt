package co.kr.woowahan_accountbook.domain.repository.setting

import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto

interface SettingRepository {
    suspend fun getPayments(): List<PaymentDto>
    suspend fun getClassificationsByType(isIncome: Boolean): List<ClassificationDto>
    suspend fun addPayment(name: String)
    suspend fun updatePayment(id: Int, name: String)
}