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
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO - 추후 Migration 부분에 대한 코드를 작성해야 한다.
    }
}