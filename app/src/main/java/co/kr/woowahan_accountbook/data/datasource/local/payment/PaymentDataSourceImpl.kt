package co.kr.woowahan_accountbook.data.datasource.local.payment

import android.content.ContentValues
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
        val sortOrder = "_ID ASC"
        val cursor = readableDatabase.query(
            "PAYMENT",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        ).apply { moveToFirst() }

        return Payment(id = cursor.getInt(0), paymentName = cursor.getString(1))
    }

    override fun getPayments(): List<Payment> {
        val sortOrder = "_ID ASC"

        val cursor = readableDatabase.query(
            "PAYMENT",
            null,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val list = mutableListOf<Payment>()
        while (cursor.moveToNext()) {
            list.add(Payment(id = cursor.getInt(0), paymentName = cursor.getString(1)))
        }
        return list
    }

    override fun insertPayment(name: String) {
        val values = ContentValues().apply {
            put("PAYMENT_NAME", name)
        }
        writableDatabase.insert("PAYMENT", null, values)
    }

    override fun updatePayment(id: Int, name: String) {
        TODO("Not yet implemented")
    }
}