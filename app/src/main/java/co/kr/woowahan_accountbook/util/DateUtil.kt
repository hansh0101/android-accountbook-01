package co.kr.woowahan_accountbook.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun getToday(): String {
        val calendar = Calendar.getInstance()
        val time = calendar.time
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd.E요일")
        return simpleDateFormat.format(time)
    }

    fun getDayOfWeek(year: Int, month: Int, day: Int): String {
        val yearString = year.toString()
        val monthString = String.format("%02d", month)
        val dayString = String.format("%02d", day)
        val date = SimpleDateFormat("yyyyMMdd").parse(yearString + monthString + dayString)
        val calendar = Calendar.getInstance()
        calendar.time = date ?: throw IllegalArgumentException()
        return DayOfWeek.stringOf(calendar.get(Calendar.DAY_OF_WEEK) - 1)
    }

    fun getDayOfWeekIndex(year: Int, month: Int, day: Int): Int {
        val yearString = year.toString()
        val monthString = String.format("%02d", month)
        val dayString = String.format("%02d", day)
        val date = SimpleDateFormat("yyyyMMdd").parse(yearString + monthString + dayString)
        val calendar = Calendar.getInstance()
        calendar.time = date ?: throw IllegalArgumentException()
        return calendar.get(Calendar.DAY_OF_WEEK) - 1
    }

    fun getPreviousMonthDayCount(year: Int, month: Int): Int {
        return when (month) {
            1, 2, 4, 6, 8, 9, 11 -> 31
            5, 7, 10, 12 -> 30
            3 -> {
                if (year % 400 == 0) 29
                else if (year % 100 == 0) 28
                else if (year % 4 == 0) 29
                else 28
            }
            else -> throw IllegalArgumentException()
        }
    }

    enum class DayOfWeek(val index: Int, val dayOfWeek: String) {
        SUNDAY(0, "일요일"),
        MONDAY(1, "월요일"),
        TUESDAY(2, "화요일"),
        WEDNESDAY(3, "수요일"),
        THURSDAY(4, "목요일"),
        FRIDAY(5, "금요일"),
        SATURDAY(6, "토요일");

        companion object {
            fun stringOf(index: Int): String {
                return values().find { it.index == index }?.dayOfWeek
                    ?: throw IllegalArgumentException()
            }
        }
    }
}