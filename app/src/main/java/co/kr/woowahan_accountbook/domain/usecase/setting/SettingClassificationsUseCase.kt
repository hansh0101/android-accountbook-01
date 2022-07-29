package co.kr.woowahan_accountbook.domain.usecase.setting

import co.kr.woowahan_accountbook.domain.entity.setting.SettingClassification
import co.kr.woowahan_accountbook.domain.repository.setting.SettingRepository
import javax.inject.Inject

class SettingClassificationsUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(isIncome: Boolean): List<SettingClassification> {
        return settingRepository.getClassificationsByType(isIncome).map {
            SettingClassification(SettingClassification.Type.BODY, it)
        }.toMutableList().apply {
            add(0, SettingClassification(SettingClassification.Type.HEADER, isIncome))
            add(SettingClassification(SettingClassification.Type.FOOTER, isIncome))
        }
    }
}