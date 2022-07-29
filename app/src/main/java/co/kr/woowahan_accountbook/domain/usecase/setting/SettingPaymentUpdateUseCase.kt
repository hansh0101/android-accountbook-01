package co.kr.woowahan_accountbook.domain.usecase.setting

import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SettingPaymentUpdateUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(id: Int, name: String) {
        settingRepository.updatePayment(id, name)
    }
}