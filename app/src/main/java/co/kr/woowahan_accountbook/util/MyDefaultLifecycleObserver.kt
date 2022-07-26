package co.kr.woowahan_accountbook.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

class MyDefaultLifecycleObserver : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Timber.tag(owner::class.java.simpleName).d("onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.tag(owner::class.java.simpleName).d("onStart")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Timber.tag(owner::class.java.simpleName).d("onResume")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Timber.tag(owner::class.java.simpleName).d("onPause")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.tag(owner::class.java.simpleName).d("onStop")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Timber.tag(owner::class.java.simpleName).d("onDestroy")
    }
}