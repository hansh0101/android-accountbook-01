package co.kr.woowahan_accountbook.domain.repository.statistics

import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem

interface StatisticsRepository {
    suspend fun getTotalAmountByClassificationType(year: Int, month: Int): List<StatisticsItem>
}