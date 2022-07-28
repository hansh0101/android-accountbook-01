package co.kr.woowahan_accountbook.di

import co.kr.woowahan_accountbook.data.datasource.local.classification.ClassificationDataSource
import co.kr.woowahan_accountbook.data.datasource.local.payment.PaymentDataSource
import co.kr.woowahan_accountbook.data.repository.SettingRepositoryImpl
import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import co.kr.woowahan_accountbook.domain.usecase.SettingPaymentsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {
    @Provides
    @Singleton
    fun provideSettingRepository(
        paymentDataSource: PaymentDataSource,
        classificationDataSource: ClassificationDataSource,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): SettingRepository =
        SettingRepositoryImpl(paymentDataSource, classificationDataSource, coroutineDispatcher)
}