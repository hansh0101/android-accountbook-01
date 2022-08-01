package co.kr.woowahan_accountbook.domain.usecase.history

import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoriesUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        incomeFlag: Boolean,
        expenditureFlag: Boolean
    ): List<HistoryItem> {
        return historyRepository.getHistories(year, month, incomeFlag, expenditureFlag).map {
            HistoryItem(HistoryItem.BODY, it)
        }.toMutableList().apply {
            var pivot = 0
            val iterator = this.listIterator()
            for (item in iterator) {
                if (item.day != pivot) {
                    iterator.previous()
                    iterator.add(HistoryItem(HistoryItem.HEADER, item.year, item.month, item.day))
                    iterator.next()
                    pivot = item.day
                }
            }
        }
    }
}