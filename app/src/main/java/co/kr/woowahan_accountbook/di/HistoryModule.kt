package co.kr.woowahan_accountbook.di

import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.data.repository.history.HistoryRepositoryImpl
import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {
    @Provides
    @Singleton
    fun provideHistoryRepository(
        historyDataSource: HistoryDataSource,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): HistoryRepository = HistoryRepositoryImpl(historyDataSource, coroutineDispatcher)
}