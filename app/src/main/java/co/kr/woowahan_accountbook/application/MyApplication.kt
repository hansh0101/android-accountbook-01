package co.kr.woowahan_accountbook.application

import android.app.Application
import androidx.viewbinding.BuildConfig
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}