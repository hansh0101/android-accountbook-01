package co.kr.woowahan_accountbook.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppDatabaseHelper @Inject constructor(
    @ApplicationContext context: Context
) : SQLiteOpenHelper(context, "APP_DATABASE", null, 1) {
    override fun onCreate(database: SQLiteDatabase) {
        val paymentCreationQuery: String =
            "CREATE TABLE if not exists PAYMENT (" +
                    "_ID integer primary key autoincrement," +
                    "PAYMENT_NAME text not null" +
                    ");"
        val classificationCreationQuery: String =
            "CREATE TABLE if not exists CLASSIFICATION (" +
                    "_ID integer primary key autoincrement," +
                    "CLASSIFICATION_TYPE text not null," +
                    "CLASSIFICATION_COLOR text not null," +
                    "IS_INCOME integer" +
                    ");"
        val historyCreationQuery: String =
            "CREATE TABLE if not exists HISTORY (" +
                    "_ID integer primary key autoincrement," +
                    "AMOUNT integer not null," +
                    "DESCRIPTION text," +
                    "YEAR integer not null," +
                    "MONTH integer not null," +
                    "DAY integer not null," +
                    "PAYMENT_ID integer," +
                    "CLASSIFICATION_ID integer," +
                    "FOREIGN KEY(PAYMENT_ID) REFERENCES PAYMENT(_ID)," +
                    "FOREIGN KEY(CLASSIFICATION_ID) REFERENCES CLASSIFICATION(_ID)" +
                    ");"

        with(database) {
            execSQL(paymentCreationQuery)
            execSQL(classificationCreationQuery)
            execSQL(historyCreationQuery)
        }

        insertDefaultItems(database)
    }

    override fun onConfigure(database: SQLiteDatabase) {
        database.setForeignKeyConstraintsEnabled(true)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val historyDropQuery = "DROP TABLE IF EXISTS HISTORY"
        val paymentDropQuery = "DROP TABLE IF EXISTS PAYMENT"
        val classificationDropQuery = "DROP TABLE IF EXISTS CLASSIFICATION"

        with(database) {
            execSQL(historyDropQuery)
            execSQL(paymentDropQuery)
            execSQL(classificationDropQuery)
        }

        onCreate(database)
    }

    override fun onDowngrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val historyDropQuery = "DROP TABLE IF EXISTS HISTORY"
        val paymentDropQuery = "DROP TABLE IF EXISTS PAYMENT"
        val classificationDropQuery = "DROP TABLE IF EXISTS CLASSIFICATION"

        with(database) {
            execSQL(historyDropQuery)
            execSQL(paymentDropQuery)
            execSQL(classificationDropQuery)
        }

        onCreate(database)
    }

    private fun insertDefaultItems(database: SQLiteDatabase) {
        val defaultPayments = listOf("????????????", "??????????????? ????????????")
        val defaultExpenditureClassifications = listOf<Triple<String, String, Int>>(
            Triple("??????", "#94D3CC", 0),
            Triple("??????/??????", "#D092E2", 0),
            Triple("?????????", "#817DCE", 0),
            Triple("??????", "#4A6CC3", 0),
            Triple("??????/??????", "#4CB8B8", 0),
            Triple("??????", "#4CA1DE", 0),
            Triple("??????/??????", "#6ED5EB", 0)
        )
        val defaultIncomeClassifications = listOf<Triple<String, String, Int>>(
            Triple("??????", "#9BD182", 1),
            Triple("??????", "#EDCF65", 1),
            Triple("??????", "#E29C4D", 1),
        )
        insertDefaultPayments(database, defaultPayments)
        insertDefaultClassifications(database, defaultExpenditureClassifications)
        insertDefaultClassifications(database, defaultIncomeClassifications)
    }

    private fun insertDefaultPayments(database: SQLiteDatabase, list: List<String>) {
        list.forEach {
            val query = "INSERT INTO PAYMENT(PAYMENT_NAME) " +
                    "VALUES ('$it')"
            database.execSQL(query)
        }
    }

    private fun insertDefaultClassifications(
        database: SQLiteDatabase,
        list: List<Triple<String, String, Int>>
    ) {
        list.forEach {
            val query =
                "INSERT INTO CLASSIFICATION(CLASSIFICATION_TYPE, CLASSIFICATION_COLOR, IS_INCOME) " +
                        "VALUES ('${it.first}', '${it.second}', '${it.third}')"
            database.execSQL(query)
        }
    }
}