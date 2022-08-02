package co.kr.woowahan_accountbook.data.repository.history

import co.kr.woowahan_accountbook.data.datasource.local.classification.ClassificationDataSource
import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.data.datasource.local.payment.PaymentDataSource
import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto
import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto
import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val paymentDataSource: PaymentDataSource,
    private val classificationDataSource: ClassificationDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : HistoryRepository {
    override suspend fun getHistories(
        year: Int,
        month: Int,
        incomeFlag: Boolean,
        expenditureFlag: Boolean
    ): List<HistoryDto> =
        withContext(coroutineDispatcher) {
            historyDataSource.getHistories(year, month, incomeFlag, expenditureFlag)
        }

    override suspend fun getPayments(): List<PaymentDto> =
        withContext(coroutineDispatcher) {
            paymentDataSource.getPayments()
        }

    override suspend fun insertHistory(
        amount: Int,
        description: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int,
        classificationId: Int
    ) = withContext(coroutineDispatcher) {
        historyDataSource.insertHistory(
            amount,
            description,
            year,
            month,
            day,
            paymentId,
            classificationId
        )
    }

    override suspend fun getClassifications(): List<ClassificationDto> =
        withContext(coroutineDispatcher) {
            classificationDataSource.getClassifications()
        }

    override suspend fun getTotalAmountByType(year: Int, month: Int, isIncome: Boolean): Int =
        withContext(coroutineDispatcher) {
            historyDataSource.getTotalAmountByType(year, month, isIncome)
        }

    override suspend fun deleteHistories(ids: List<Int>) {
        withContext(coroutineDispatcher) {
            historyDataSource.deleteHistories(ids)
        }
    }
}