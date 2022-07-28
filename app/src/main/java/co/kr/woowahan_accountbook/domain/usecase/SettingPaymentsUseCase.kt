package co.kr.woowahan_accountbook.domain.usecase

import co.kr.woowahan_accountbook.domain.entity.setting.SettingPayment
import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingPaymentsUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(): List<SettingPayment> {
        return settingRepository.getPayments().map {
            SettingPayment(SettingPayment.Type.BODY, it)
        }.toMutableList().apply {
            add(0, SettingPayment(SettingPayment.Type.HEADER, 0, ""))
            add(SettingPayment(SettingPayment.Type.FOOTER, 0, ""))
        }
    }
}