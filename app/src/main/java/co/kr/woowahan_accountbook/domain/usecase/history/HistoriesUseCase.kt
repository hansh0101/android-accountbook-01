package co.kr.woowahan_accountbook.domain.usecase.history

import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem
import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoriesUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(year: Int, month: Int): List<HistoryItem> {
        return historyRepository.getHistories(year, month).map {
            HistoryItem(HistoryItem.BODY, it)
        }.toMutableList().apply {
            var pivot = 0
            val iterator = this.listIterator()
            while (iterator.hasNext()) {
                val data = iterator.next()
                if(pivot != data.day) {
                    iterator.previous()
                    iterator.add(HistoryItem(HistoryItem.HEADER, data.year, data.month, data.day))
                    pivot = data.day
                    iterator.next()
                }
            }
            iterator.previous()
            val data = iterator.next()
            if(pivot != data.day) {
                iterator.previous()
                iterator.add(HistoryItem(HistoryItem.HEADER, data.year, data.month, data.day))
            }
        }
    }
}