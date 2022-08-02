package co.kr.woowahan_accountbook.domain.repository.calendar

import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto
import co.kr.woowahan_accountbook.domain.entity.history.HistoryItem

interface CalendarRepository {
    suspend fun getHistories(year: Int, month: Int): List<HistoryDto>
}