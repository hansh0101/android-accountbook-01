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
        val paymentDropQuery = "DROP TABLE IF EXISTS PAYMENT"
        val classificationDropQuery = "DROP TABLE IF EXISTS CLASSIFICATION"
        val historyDropQuery = "DROP TABLE IF EXISTS HISTORY"

        with(database) {
            execSQL(paymentDropQuery)
            execSQL(classificationDropQuery)
            execSQL(historyDropQuery)
        }

        onCreate(database)
    }

    override fun onDowngrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val paymentDropQuery = "DROP TABLE IF EXISTS PAYMENT"
        val classificationDropQuery = "DROP TABLE IF EXISTS CLASSIFICATION"
        val historyDropQuery = "DROP TABLE IF EXISTS HISTORY"

        with(database) {
            execSQL(paymentDropQuery)
            execSQL(classificationDropQuery)
            execSQL(historyDropQuery)
        }

        onCreate(database)
    }

    private fun insertDefaultItems(database: SQLiteDatabase) {
        val defaultPayments = listOf("현대카드", "카카오뱅크 체크카드")
        val defaultExpenditureClassifications = listOf<Triple<String, String, Int>>(
            Triple("교통", "#94D3CC", 0),
            Triple("문화/여가", "#D092E2", 0),
            Triple("미분류", "#817DCE", 0),
            Triple("생활", "#4A6CC3", 0),
            Triple("쇼핑/뷰티", "#4CB8B8", 0),
            Triple("식비", "#4CA1DE", 0),
            Triple("의료/건강", "#6ED5EB", 0)
        )
        val defaultIncomeClassifications = listOf<Triple<String, String, Int>>(
            Triple("월급", "#9BD182", 1),
            Triple("용돈", "#EDCF65", 1),
            Triple("기타", "#E29C4D", 1),
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