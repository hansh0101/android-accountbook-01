package co.kr.woowahan_accountbook.di

import android.database.sqlite.SQLiteDatabase
import co.kr.woowahan_accountbook.data.datasource.local.classification.ClassificationDataSource
import co.kr.woowahan_accountbook.data.datasource.local.classification.ClassificationDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClassificationModule {
    @Provides
    @Singleton
    fun provideClassificationDataSource(
        @ReadableDatabase readableDatabase: SQLiteDatabase,
        @WritableDatabase writableDatabase: SQLiteDatabase
    ): ClassificationDataSource =
        ClassificationDataSourceImpl(readableDatabase, writableDatabase)
}