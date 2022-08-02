package co.kr.woowahan_accountbook.di

import co.kr.woowahan_accountbook.data.datasource.local.history.HistoryDataSource
import co.kr.woowahan_accountbook.data.repository.statistics.StatisticsRepositoryImpl
import co.kr.woowahan_accountbook.domain.repository.statistics.StatisticsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StatisticsModule {
    @Provides
    @Singleton
    fun provideStatisticsRepository(
        historyDataSource: HistoryDataSource,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): StatisticsRepository =
        StatisticsRepositoryImpl(historyDataSource, coroutineDispatcher)
}