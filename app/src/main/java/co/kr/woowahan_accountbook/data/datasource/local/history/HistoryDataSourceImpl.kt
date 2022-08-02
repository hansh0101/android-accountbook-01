package co.kr.woowahan_accountbook.data.datasource.local.history

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.domain.entity.dto.HistoryDto
import co.kr.woowahan_accountbook.domain.entity.statistics.StatisticsItem
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val readableDatabase: SQLiteDatabase,
    private val writableDatabase: SQLiteDatabase
) : HistoryDataSource {
    override fun getHistory(id: Int): HistoryDto {
        val query =
            "SELECT HISTORY._ID, AMOUNT, DESCRIPTION, YEAR, MONTH, DAY, PAYMENT_ID, CLASSIFICATION_ID, PAYMENT_NAME, CLASSIFICATION_TYPE, CLASSIFICATION_COLOR, IS_INCOME " +
                    "FROM HISTORY " +
                    "INNER JOIN PAYMENT " +
                    "ON PAYMENT_ID = PAYMENT._ID " +
                    "INNER JOIN CLASSIFICATION " +
                    "ON CLASSIFICATION_ID = CLASSIFICATION._ID " +
                    "WHERE HISTORY._ID = $id"
        val cursor = readableDatabase.rawQuery(query, null).apply { moveToFirst() }

        val history = HistoryDto(
            id = cursor.getInt(0),
            amount = cursor.getInt(1),
            description = cursor.getString(2),
            year = cursor.getInt(3),
            month = cursor.getInt(4),
            day = cursor.getInt(5),
            paymentId = cursor.getInt(6),
            classificationId = cursor.getInt(7),
            paymentName = cursor.getString(8),
            classificationType = cursor.getString(9),
            classificationColor = cursor.getString(10),
            isIncome = cursor.getInt(11) == 1
        )
        cursor.close()
        return history
    }

    override fun getHistories(
        year: Int,
        month: Int,
        incomeFlag: Boolean,
        expenditureFlag: Boolean
    ): List<HistoryDto> {
        val income = if (incomeFlag) 1 else 0
        val expenditure = if (expenditureFlag) 0 else 1
        if (income == 0 && expenditure == 1) return listOf()
        val query =
            "SELECT HISTORY._ID, AMOUNT, DESCRIPTION, YEAR, MONTH, DAY, PAYMENT_ID, CLASSIFICATION_ID, PAYMENT_NAME, CLASSIFICATION_TYPE, CLASSIFICATION_COLOR, IS_INCOME " +
                    "FROM HISTORY " +
                    "INNER JOIN PAYMENT " +
                    "ON PAYMENT_ID = PAYMENT._ID " +
                    "INNER JOIN CLASSIFICATION " +
                    "ON CLASSIFICATION_ID = CLASSIFICATION._ID " +
                    "WHERE YEAR = $year AND MONTH = $month AND (IS_INCOME = $income OR IS_INCOME = $expenditure) " +
                    "ORDER BY DAY desc, HISTORY._ID desc"
        val cursor = readableDatabase.rawQuery(query, null)
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
                    classificationId = cursor.getInt(7),
                    paymentName = cursor.getString(8),
                    classificationType = cursor.getString(9),
                    classificationColor = cursor.getString(10),
                    isIncome = cursor.getInt(11) == 1
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

    override fun deleteHistories(ids: List<Int>) {
        ids.forEach { id ->
            val selection = "_ID LIKE ?"
            val selectionArgs = arrayOf("$id")
            writableDatabase.delete("HISTORY", selection, selectionArgs)
        }
    }

    override fun getTotalAmountByType(year: Int, month: Int, isIncome: Boolean): Int {
        val query =
            "SELECT sum(AMOUNT) " +
                    "FROM HISTORY " +
                    "INNER JOIN CLASSIFICATION " +
                    "ON CLASSIFICATION_ID = CLASSIFICATION._ID " +
                    "WHERE YEAR = $year AND MONTH = $month AND CLASSIFICATION.IS_INCOME = ${if (isIncome) 1 else 0}"
        val cursor = readableDatabase.rawQuery(query, null).apply { moveToFirst() }
        return cursor.getInt(0)
    }

    override fun getTotalAmountByClassificationType(year: Int, month: Int): List<StatisticsItem> {
        val query =
            "SELECT sum(AMOUNT), CLASSIFICATION_TYPE, CLASSIFICATION_COLOR " +
                    "FROM HISTORY " +
                    "INNER JOIN CLASSIFICATION " +
                    "ON CLASSIFICATION_ID = CLASSIFICATION._ID " +
                    "WHERE YEAR = $year AND MONTH = $month AND CLASSIFICATION.IS_INCOME = 0 " +
                    "GROUP BY CLASSIFICATION_ID " +
                    "ORDER BY sum(AMOUNT) desc"
        val cursor = readableDatabase.rawQuery(query, null)
        val statisticsItems = mutableListOf<StatisticsItem>()
        while (cursor.moveToNext()) {
            statisticsItems.add(
                StatisticsItem(
                    amount = cursor.getInt(0),
                    classificationType = cursor.getString(1),
                    classificationColor = cursor.getString(2)
                )
            )
        }
        cursor.close()
        return statisticsItems
    }
}