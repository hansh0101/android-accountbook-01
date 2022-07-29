package co.kr.woowahan_accountbook.domain.usecase.setting

import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingClassificationUpdateUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(id: Int, type: String, color: String, isIncome: Boolean) {
        settingRepository.updateClassification(id, type, color, isIncome)
    }
}