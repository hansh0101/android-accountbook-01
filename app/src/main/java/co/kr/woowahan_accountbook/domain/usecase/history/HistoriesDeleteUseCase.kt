package co.kr.woowahan_accountbook.domain.usecase.history

import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoriesDeleteUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(ids: List<Int>) {
        historyRepository.deleteHistories(ids)
    }
}