package co.kr.woowahan_accountbook.domain.usecase.history

import co.kr.woowahan_accountbook.domain.entity.dto.ClassificationDto
import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoryClassificationsUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(isIncome: Boolean): List<ClassificationDto> {
        return historyRepository.getClassifications().filter { it.isIncome == isIncome }
            .toMutableList().apply {
                add(ClassificationDto(0, "추가하기", "", isIncome))
                add(ClassificationDto(0, "선택하세요", "", isIncome))
            }
    }
}