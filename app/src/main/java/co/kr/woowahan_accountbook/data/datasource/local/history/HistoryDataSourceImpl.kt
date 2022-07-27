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
        val selection = "_ID = ?"
        val selectionArgs = arrayOf("$id")
        val cursor = readableDatabase.query(
            "HISTORY",
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        ).apply { moveToFirst() }
        val history = HistoryDto(
            id = cursor.getInt(0),
            amount = cursor.getInt(1),
            description = cursor.getString(2),
            year = cursor.getInt(3),
            month = cursor.getInt(4),
            day = cursor.getInt(5),
            paymentId = cursor.getInt(6),
            classificationId = cursor.getInt(7)
        )
        cursor.close()
        return history
    }

    override fun getHistories(): List<HistoryDto> {
        val sortOrder = "_ID ASC"
        val cursor = readableDatabase.query(
            "HISTORY",
            null,
            null,
            null,
            null,
            null,
            sortOrder
        )
        val histories = mutableListOf<HistoryDto>()
        while (cursor.moveToNext()) {
            histories.add(
                HistoryDto(
                    id = cursor.getInt(0),
                    amount = cursor.getInt(1),
                    description = cursor.getString(2),
                    year = cursor.getInt(3),
                    month = cursor.getInt(4),
                    day = cursor.getInt(5),
                    paymentId = cursor.getInt(6),
                    classificationId = cursor.getInt(7)
                )
            )
        }
        cursor.close()
        return histories
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
        val values = ContentValues().apply {
            put("AMOUNT", amount)
            put("DESCRIPTION", description)
            put("YEAR", year)
            put("MONTH", month)
            put("DAY", day)
            put("PAYMENT_ID", paymentId)
            put("CLASSIFICATION_ID", classificationId)
        }
        val selection = "_ID LIKE ?"
        val selectionArgs = arrayOf("$id")
        writableDatabase.update(
            "HISTORY",
            values,
            selection,
            selectionArgs
        )
    }

    override fun deleteHistory(id: Int) {
        TODO("Not yet implemented")
    }
}