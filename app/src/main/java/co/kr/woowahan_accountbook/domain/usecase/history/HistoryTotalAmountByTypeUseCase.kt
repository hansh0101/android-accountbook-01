package co.kr.woowahan_accountbook.domain.usecase.history

import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoryTotalAmountByTypeUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(year: Int, month: Int, isIncome: Boolean): Int =
        historyRepository.getTotalAmountByType(year, month, isIncome)
}