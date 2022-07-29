package co.kr.woowahan_accountbook.domain.repository.history

import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem

interface HistoryRepository {
    suspend fun getHistories(year: Int, month: Int): List<HistoryDto>
}