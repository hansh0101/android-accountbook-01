package co.kr.woowahan_accountbook.presentation.ui.main

import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import co.kr.woowahan_accountbook.R
import co.kr.woowahan_accountbook.databinding.ActivityMainBinding
import co.kr.woowahan_accountbook.presentation.ui.base.BaseActivity
import co.kr.woowahan_accountbook.presentation.ui.main.calendar.CalendarFragment
import co.kr.woowahan_accountbook.presentation.ui.main.history.HistoryFragment
import co.kr.woowahan_accountbook.presentation.ui.main.setting.SettingFragment
import co.kr.woowahan_accountbook.presentation.ui.main.statistics.StatisticsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.bnvMain.selectedItemId = R.id.menu_history
        supportFragmentManager.commit {
            add<HistoryFragment>(R.id.fcv_main)
        }
        binding.bnvMain.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.menu_history -> {
                    supportFragmentManager.commit {
                        replace<HistoryFragment>(R.id.fcv_main)
                    }
                    true
                }
                R.id.menu_calendar -> {
                    supportFragmentManager.commit {
                        replace<CalendarFragment>(R.id.fcv_main)
                    }
                    true
                }
                R.id.menu_statistics -> {
                    supportFragmentManager.commit {
                        replace<StatisticsFragment>(R.id.fcv_main)
                    }
                    true
                }
                R.id.menu_setting -> {
                    supportFragmentManager.commit {
                        replace<SettingFragment>(R.id.fcv_main)
                    }
                    true
                }
                else -> throw IllegalArgumentException()
            }
        }
    }
}