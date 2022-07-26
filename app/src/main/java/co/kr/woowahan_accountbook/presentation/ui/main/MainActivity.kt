package co.kr.woowahan_accountbook.presentation.ui.main

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.data.datasource.local.payment.PaymentDataSource
import co.kr.woowahan_accountbook.databinding.ActivityMainBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    @Inject
    lateinit var paymentDataSource: PaymentDataSource

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        paymentDataSource.insertPayment("국민카드")
    }
}