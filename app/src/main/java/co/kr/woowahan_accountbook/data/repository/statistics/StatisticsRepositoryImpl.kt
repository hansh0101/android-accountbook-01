package co.kr.woowahan_accountbook.data.repository.statistics

import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem
import co.kr.woowahan_accountbook.domain.repository.statistics.StatisticsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : StatisticsRepository {
    override suspend fun getTotalAmountByClassificationType(
        year: Int,
        month: Int
    ): List<StatisticsItem> =
        withContext(coroutineDispatcher) {
            historyDataSource.getTotalAmountByClassificationType(year, month)
        }
}