package co.kr.woowahan_accountbook.domain.usecase.history

import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto
import co.kr.woowahan_accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoryPaymentsUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(): List<PaymentDto> =
        historyRepository.getPayments().toMutableList().apply {
            add(PaymentDto(0, "추가하기"))
            add(PaymentDto(0, "선택하세요"))
        }
}