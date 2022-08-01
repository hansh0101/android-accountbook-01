package co.kr.woowahan_accountbook.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val calendar = Calendar.getInstance()
    private val time = calendar.time
    private val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd.E요일")
    val date = simpleDateFormat.format(time)
}