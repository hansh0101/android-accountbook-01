package co.kr.woowahan_accountbook.domain.usecase.calendar

import co.kr.woowahan_accountbook.domain.entity.calendar.CalendarItem
import co.kr.woowahan_accountbook.domain.repository.calendar.CalendarRepository
import javax.inject.Inject

class CalendarUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(year: Int, month: Int): List<CalendarItem> {
        val histories = calendarRepository.getHistories(year, month)
        val dayCount = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> {
                if (year % 400 == 0) 29
                else if (year % 100 == 0) 28
                else if (year % 4 == 0) 29
                else 28
            }
            else -> throw IllegalArgumentException()
        }

        val calendarItems = mutableListOf<CalendarItem>()
        for (day in 1..dayCount) {
            val incomeAmount = histories.filter { it.day == day && it.isIncome }.sumOf { it.amount }
            val expenditureAmount =
                -1 * histories.filter { it.day == day && !it.isIncome }.sumOf { it.amount }
            val totalAmount = incomeAmount + expenditureAmount
            calendarItems.add(CalendarItem(year, month, day, incomeAmount, expenditureAmount, totalAmount))
        }
        return calendarItems
    }
}