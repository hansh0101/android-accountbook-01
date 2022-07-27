package co.kr.woowahan_accountbook.data.datasource.local.history

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.data.dto.HistoryDto
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val readableDatabase: SQLiteDatabase,
    private val writableDatabase: SQLiteDatabase
) : HistoryDataSource {
    override fun getHistory(id: Int): HistoryDto {
        TODO("Not yet implemented")
    }

    override fun getHistories(): List<HistoryDto> {
        TODO("Not yet implemented")
    }

    override fun insertHistory(
        amount: Int,
        description: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int,
        classificationId: Int
    ) {
        val values = ContentValues().apply {
            put("AMOUNT", amount)
            put("DESCRIPTION", description)
            put("YEAR", year)
            put("MONTH", month)
            put("DAY", day)
            put("PAYMENT_ID", paymentId)
            put("CLASSIFICATION_ID", classificationId)
        }
        writableDatabase.insert("HISTORY", null, values)
    }

    override fun updateHistory(
        id: Int,
        amount: Int,
        description: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int,
        classificationId: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteHistory(id: Int) {
        TODO("Not yet implemented")
    }
}