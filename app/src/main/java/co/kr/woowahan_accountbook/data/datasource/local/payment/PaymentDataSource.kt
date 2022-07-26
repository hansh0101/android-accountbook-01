package co.kr.woowahan_accountbook.data.datasource.local.payment

import co.kr.woowahan_accountbook.data.dto.Payment

interface PaymentDataSource {
    fun getPayment(id: Int): Payment
    fun getPayments(): List<Payment>
    fun insertPayment(name: String)
    fun updatePayment(id: Int, name: String)
}