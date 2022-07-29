package co.kr.woowahan_accountbook.di

import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.data.datasource.local.classification.ClassificationDataSource
import co.kr.woowahan_accountbook.data.datasource.local.classification.ClassificationDataSourceImpl
import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSourceImpl
import co.kr.woowahan_accountbook.data.datasource.local.payment.PaymentDataSource
import co.kr.woowahan_accountbook.data.datasource.local.payment.PaymentDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideClassificationDataSource(
        @ReadableDatabase readableDatabase: SQLiteDatabase,
        @WritableDatabase writableDatabase: SQLiteDatabase
    ): ClassificationDataSource = ClassificationDataSourceImpl(readableDatabase, writableDatabase)

    @Provides
    @Singleton
    fun provideHistoryDataSource(
        @ReadableDatabase readableDatabase: SQLiteDatabase,
        @WritableDatabase writableDatabase: SQLiteDatabase
    ): HistoryDataSource = HistoryDataSourceImpl(readableDatabase, writableDatabase)

    @Provides
    @Singleton
    fun providePaymentDatasource(
        @ReadableDatabase readableDatabase: SQLiteDatabase,
        @WritableDatabase writableDatabase: SQLiteDatabase
    ): PaymentDataSource = PaymentDataSourceImpl(readableDatabase, writableDatabase)
}