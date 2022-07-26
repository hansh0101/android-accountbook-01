package co.kr.woowahan_accountbook.di

import android.content.Context
import co.kr.woowahan_accountbook.data.database.AppDatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @WritableDatabase
    @Provides
    @Singleton
    fun provideWritableAppDatabase(@ApplicationContext context: Context) =
        AppDatabaseHelper(context).writableDatabase

    @ReadableDatabase
    @Provides
    @Singleton
    fun provideReadableAppDatabase(@ApplicationContext context: Context) =
        AppDatabaseHelper(context).readableDatabase
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WritableDatabase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReadableDatabase