package co.kr.woowahan_accountbook.domain.usecase.setting

import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingPaymentAddUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(name: String) {
        settingRepository.addPayment(name)
    }
}