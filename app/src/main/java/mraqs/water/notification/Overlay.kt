package mraqs.water.notification

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.doubleclick.PublisherAdView
import mraqs.water.R
import mraqs.water.ui.main.home.HomeActivity

class Overlay : JobService() {

    private val TAG = "Overlay"
    private lateinit var windowManager: WindowManager
    private lateinit var view: View

    override fun onStartJob(param: JobParameters?): Boolean {

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val type = if (VERSION.SDK_INT >= VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER

        view = inflater.inflate(R.layout.overlay, null) as CardView
        val btnClose = view.findViewById<Button>(R.id.btn_later)
        val btnDrink = view.findViewById<Button>(R.id.btn_drink)

        val mPublisherAdView = view.findViewById<PublisherAdView>(R.id.publisherAdView)
        val adRequest = PublisherAdRequest.Builder().build()
        mPublisherAdView.loadAd(adRequest)

        btnClose.setOnClickListener {
            windowManager.removeView(view)
            stopService(Intent(applicationContext, Overlay::class.java))
        }

        btnDrink.setOnClickListener {
            windowManager.removeView(view)
            stopService(Intent(applicationContext, Overlay::class.java))

            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        if (ViewCompat.isAttachedToWindow(view)) {
            windowManager.removeView(view)
        } else {
            windowManager.addView(view, params)
        }
        Log.d(TAG, "onStartJob: Overlay drawed")
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        if (ViewCompat.isAttachedToWindow(view)) {
            windowManager.removeView(view)
        }
        stopService(Intent(applicationContext, Overlay::class.java))
        return true
    }
}

