package co.kr.woowahan_accountbook.data.datasource.local.payment

import co.kr.woowahan_accountbook.data.dto.PaymentDto

interface PaymentDataSource {
    fun getPayment(id: Int): PaymentDto
    fun getPayments(): List<PaymentDto>
    fun insertPayment(name: String)
    fun updatePayment(id: Int, name: String)
}