package co.kr.woowahan_accountbook.data.repository.history

import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : HistoryRepository {
    override suspend fun getHistories(year: Int, month: Int): List<HistoryDto> =
        withContext(coroutineDispatcher) {
            historyDataSource.getHistories(year, month)
        }
}