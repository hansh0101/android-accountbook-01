package co.kr.woowahan_accountbook.domain.entity.setting

import co.kr.woowahan_accountbook.domain.entity.dto.PaymentDto

data class SettingPayment(
    val type: Int,
    val id: Int,
    val paymentName: String
) {
    constructor(type: Int): this(type, 0, "")
    constructor(type: Int, payment: PaymentDto) : this(type, payment.id, payment.paymentName)

    companion object Type {
        const val HEADER = 0
        const val BODY = 1
        const val FOOTER = 2
    }
}
