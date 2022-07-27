package co.kr.woowahan_accountbook.presentation.ui.main

import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ActivityMainBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_main
}