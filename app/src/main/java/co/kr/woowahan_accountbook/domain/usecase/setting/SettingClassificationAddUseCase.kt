package co.kr.woowahan_accountbook.domain.usecase.setting

import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingClassificationAddUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(type: String, color: String, isIncome: Boolean) {
        settingRepository.addClassification(type, color, isIncome)
    }
}