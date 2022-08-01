package co.kr.woowahan_accountbook.domain.repository.history

import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto
import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem

interface HistoryRepository {
    suspend fun getHistories(year: Int, month: Int): List<HistoryDto>
    suspend fun getPayments(): List<PaymentDto>
    suspend fun getClassifications(): List<ClassificationDto>
    suspend fun insertHistory(
        amount: Int,
        description: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int,
        classificationId: Int
    )
}