package co.kr.woowahan_accountbook.data.datasource.local.payment

import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.data.dto.Payment
import javax.inject.Inject

class PaymentDataSourceImpl @Inject constructor(
    private val readableDatabase: SQLiteDatabase,
    private val writableDatabase: SQLiteDatabase
) : PaymentDataSource {
    override fun getPayment(id: Int): Payment {
        val projection = arrayOf("_ID", "PAYMENT_NAME")
        val selection = "_ID = ?"
        val selectionArgs = arrayOf("$id")
        val sortOrder = "_ID DESC"

        val cursor = readableDatabase.query(
            "PAYMENT",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        ).apply { moveToFirst() }

        return Payment(cursor.getInt(0), cursor.getString(1))
    }

    override fun getPayments(): List<Payment> {
        TODO("Not yet implemented")
    }

    override fun insertPayment(name: String) {
        val query: String =
            "INSERT into PAYMENT (PAYMENT_NAME) values ('$name');"
        writableDatabase.execSQL(query)
    }

    override fun updatePayment(id: Int, name: String) {
        TODO("Not yet implemented")
    }
}