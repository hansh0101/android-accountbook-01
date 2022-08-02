package co.kr.woowahan_accountbook.domain.usecase.statistics

import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem
import co.kr.woowahan_accountbook.domain.repository.statistics.StatisticsRepository
import javax.inject.Inject

class StatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(year: Int, month: Int): List<StatisticsItem> =
        statisticsRepository.getTotalAmountByClassificationType(year, month)
}