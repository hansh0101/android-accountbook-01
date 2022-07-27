package co.kr.woowahan_accountbook.di

import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {
    @Provides
    @Singleton
    fun provideHistoryDataSource(
        @ReadableDatabase readableDatabase: SQLiteDatabase,
        @WritableDatabase writableDatabase: SQLiteDatabase
    ): HistoryDataSource = HistoryDataSourceImpl(readableDatabase, writableDatabase)
}