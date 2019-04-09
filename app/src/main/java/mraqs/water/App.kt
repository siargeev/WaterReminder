package mraqs.water

import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import mraqs.water.di.component.DaggerAppComponent

class App : DaggerApplication(), LifecycleObserver {

    companion object {
        val appInBackground = MutableLiveData<Boolean>()
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        val config = YandexMetricaConfig.newConfigBuilder(getString(R.string.yandex_metrica_id)).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }

    @OnLifecycleEvent(ON_START)
    private fun onMoveToForeground() {
        appInBackground.postValue(false)
    }

    @OnLifecycleEvent(ON_STOP)
    private fun onMoveToBackground() {
        appInBackground.postValue(true)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}