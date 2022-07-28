package co.kr.woowahan_accountbook.data.repository

import co.kr.woowahan_accountbook.data.datasource.local.classification.ClassificationDataSource
import co.kr.woowahan_accountbook.data.datasource.local.payment.PaymentDataSource
import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto
import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val paymentDataSource: PaymentDataSource,
    private val classificationDataSource: ClassificationDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : SettingRepository {
    override suspend fun getPayments(): List<PaymentDto> =
        withContext(coroutineDispatcher) {
            paymentDataSource.getPayments()
        }

    override suspend fun getClassificationsByType(isIncome: Boolean): List<ClassificationDto> =
        withContext(coroutineDispatcher) {
            classificationDataSource.getClassificationsByType(isIncome)
        }

    override suspend fun addPayment(name: String) {
        withContext(coroutineDispatcher) {
            paymentDataSource.insertPayment(name)
        }
    }

    override suspend fun updatePayment(id: Int, name: String) {
        withContext(coroutineDispatcher) {
            paymentDataSource.updatePayment(id, name)
        }
    }
}