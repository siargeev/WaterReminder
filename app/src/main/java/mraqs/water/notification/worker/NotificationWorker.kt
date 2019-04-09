package mraqs.water.notification.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import mraqs.water.App
import mraqs.water.R
import mraqs.water.manager.NetManager
import mraqs.water.ui.main.home.HomeActivity

class NotificationWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        if (App.appInBackground.value!! && !NetManager(context).isConnectedToInternet)
            showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val intent = Intent(context, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val remoteView = RemoteViews(context.packageName, R.layout.notification)

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val channel = NotificationChannel("my_channel_1", "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "my_channel_1")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setCustomContentView(remoteView)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Drink", pendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()

        manager.notify(5360, notification)
    }
}