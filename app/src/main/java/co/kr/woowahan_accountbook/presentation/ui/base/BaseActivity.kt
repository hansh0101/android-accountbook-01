package co.kr.woowahan_accountbook.presentation.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.kr.woowahan_accountbook.util.MyDefaultLifecycleObserver

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: T
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(MyDefaultLifecycleObserver())
        binding = DataBindingUtil.setContentView(this, layoutRes)
    }
}