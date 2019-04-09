package mraqs.water.ui.splash

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.manager.NetManager
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.main.home.HomeActivity
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var netManager: NetManager, private var prefs: LivePreferenceManager) : ViewModel() {


    private lateinit var waitingThread: Thread

    fun startLoading(context: Context) {
        initThreads(context)
        startThreads()
    }

    private fun initThreads(context: Context) {
        if (netManager.isConnectedToInternet)
            initAdMob(context)
        initWaitingThread(context)
    }

    private fun initAdMob(context: Context) {
        //todo delete comment
//        MobileAds.initialize(context, context.getString(R.string.google_app_id))
    }

    private fun startThreads() {
        startWaitingThread()
    }

    private fun initWaitingThread(context: Context) {
        waitingThread = Thread {
            Thread.sleep(3 * 1000)
            showNextActivity(context)
        }.apply { priority = Thread.MIN_PRIORITY }
    }

    private fun startWaitingThread() {
        waitingThread.start()
    }

    private fun showNextActivity(context: Context) {
        val intent: Intent = if (prefs.isFirstLaunch()) {
            prefs.registerFirstLaunch()
            Intent(context, IntroActivity::class.java)
//            Intent(context, GdprActivity::class.java)
        } else {
            Intent(context, HomeActivity::class.java)
        }
        context.startActivity(intent)
    }
}