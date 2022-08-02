package co.kr.woowahan_accountbook.domain.entity.calendar

data class CalendarItem(
    val year: Int,
    val month: Int,
    val day: Int,
    val income: Int,
    val expenditure: Int,
    val total: Int
)
