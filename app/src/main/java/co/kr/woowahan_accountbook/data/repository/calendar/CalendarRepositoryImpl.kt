package co.kr.woowahan_accountbook.data.repository.calendar

import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.repository.calendar.CalendarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : CalendarRepository {
    override suspend fun getHistories(year: Int, month: Int): List<HistoryDto> =
        withContext(coroutineDispatcher) {
            historyDataSource.getHistories(year, month, incomeFlag = true, expenditureFlag = true)
        }
}