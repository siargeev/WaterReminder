package mraqs.water.notification.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import mraqs.water.App
import mraqs.water.R
import mraqs.water.manager.NetManager
import mraqs.water.ui.main.home.HomeActivity

class NotificationWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {


    companion object {
        private const val TAG = "Work"
        private const val CHANNEL_ID = "my_channel_5345"
        const val NOTIFICATION_ID = 5345
    }

    override fun doWork(): Result {
        if (App.appInBackground.value!! && !NetManager(context).isConnectedToInternet)
            showNotification()
        Log.d(TAG, "doWork: Notification Work Complete")
        return Result.success()
    }

    private fun showNotification() {
        val intent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentText(context.getString(R.string.time_to_drink))
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_drop_active, context.getString(R.string.drink_action), pendingIntent)
            .build()

        manager.notify(NOTIFICATION_ID, notification)
    }
}