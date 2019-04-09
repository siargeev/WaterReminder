package mraqs.notification.notification

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import mraqs.water.R
import mraqs.water.ui.main.home.HomeActivity

class Overlay : JobService() {

    override fun onStartJob(param: JobParameters?): Boolean {

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val type = if (VERSION.SDK_INT >= VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER

        val view = inflater.inflate(R.layout.overlay, null) as ConstraintLayout
        val btnClose = view.findViewById<Button>(R.id.btn_later)
        val btnDrink = view.findViewById<Button>(R.id.btn_drink)

        //todo configure Ad
//        val mPublisherAdView = view.findViewById<PublisherAdView>(R.id.publisherAdView)
//        val adRequest = PublisherAdRequest.Builder().build()
//        mPublisherAdView.loadAd(adRequest)

        btnClose.setOnClickListener {
            windowManager.removeView(view)
            stopService(Intent(applicationContext, Overlay::class.java))
        }

        btnDrink.setOnClickListener {
            windowManager.removeView(view)
            stopService(Intent(applicationContext, Overlay::class.java))

            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
//            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        }

        windowManager.addView(view, params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}
