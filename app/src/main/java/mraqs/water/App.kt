package mraqs.water

import androidx.lifecycle.LifecycleObserver
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import mraqs.water.di.component.DaggerAppComponent

class App : DaggerApplication(), LifecycleObserver {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}