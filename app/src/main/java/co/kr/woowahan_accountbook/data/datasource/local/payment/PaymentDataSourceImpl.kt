package co.kr.woowahan_accountbook.data.datasource.local.payment

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.data.dto.Payment
import timber.log.Timber
import javax.inject.Inject

class PaymentDataSourceImpl @Inject constructor(
    private val readableDatabase: SQLiteDatabase,
    private val writableDatabase: SQLiteDatabase
) : PaymentDataSource {
    override fun getPayment(id: Int): Payment {
        val query: String =
            "SELECT * FROM PAYMENT WHERE ID = '$id';"
        val cursor = readableDatabase.rawQuery(query, null)
        return Payment(
            id = cursor.getInt(0),
            paymentName = cursor.getString(1)
        )
    }

    override fun getPayments(): List<Payment> {
        TODO("Not yet implemented")
    }

    override fun insertPayment(name: String) {
        val contentValues = ContentValues().apply {
            put("PAYMENT_NAME", name)
        }
    }

    override fun updatePayment(id: Int, name: String) {
        TODO("Not yet implemented")
    }
}