package mraqs.water.manager

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.ExistingPeriodicWorkPolicy.REPLACE
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import mraqs.notification.notification.worker.NotificationWorker
import mraqs.water.notification.worker.OverlayWorker
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MINUTES
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderManager @Inject constructor(private var applicationContext: Context, private val prefs: LivePreferenceManager) {


    private val TAG = "ReminderManager"

    fun startNotificationReminder() {
        if (prefs.isNotificationReminderDisabled()) {
            prefs.enableNotificationReminder()
            val reminderInterval = prefs.getReminderInterval()
            val notificationRequest = PeriodicWorkRequest
                .Builder(NotificationWorker::class.java, reminderInterval, TimeUnit.MINUTES, reminderInterval - 1, MINUTES)
                .addTag(NOTIFICATION_WORK)
                .build()
            WorkManager.getInstance().enqueueUniquePeriodicWork(NOTIFICATION_WORK, KEEP, notificationRequest)
            Log.d(TAG, "startNotificationReminder: ${WorkManager.getInstance().getWorkInfosByTag(NOTIFICATION_WORK).get()}")
        }
    }

    fun startOverlayReminder() {
        if (prefs.isOverlayReminderDisabled()) {
            prefs.enableOverlayReminder()
            val reminderInterval = prefs.getReminderInterval()
            val overlayRequest = PeriodicWorkRequest
                .Builder(OverlayWorker::class.java, reminderInterval, TimeUnit.MINUTES, reminderInterval - 1, MINUTES)
                .addTag(OVERLAY_WORK)
                .build()
            WorkManager.getInstance().enqueueUniquePeriodicWork(OVERLAY_WORK, KEEP, overlayRequest)
            Log.d(TAG, "startOverlayReminder: ${WorkManager.getInstance().getWorkInfosByTag(OVERLAY_WORK).get()}")
        }
    }

    fun updateRemindersInterval(newInterval: Long) {
        prefs.updateReminderInterval(newInterval)
        updateNotificationReminderInterval(newInterval)
        updateOverlayReminderInterval(newInterval)
    }

    private fun updateOverlayReminderInterval(newInterval: Long) {
        val newOverlayRequest = PeriodicWorkRequest
            .Builder(OverlayWorker::class.java, newInterval, MINUTES, newInterval - 1, MINUTES)
            .addTag(ReminderManager.OVERLAY_WORK)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(ReminderManager.OVERLAY_WORK, REPLACE, newOverlayRequest)
        Log.d(TAG, "updateOverlayReminder: ${WorkManager.getInstance().getWorkInfosByTag(ReminderManager.OVERLAY_WORK).get()}")
    }

    private fun updateNotificationReminderInterval(newInterval: Long) {
        val newNotificationRequest = PeriodicWorkRequest
            .Builder(NotificationWorker::class.java, newInterval, MINUTES, newInterval - 1, MINUTES)
            .addTag(ReminderManager.NOTIFICATION_WORK)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(ReminderManager.NOTIFICATION_WORK, REPLACE, newNotificationRequest)
        Log.d(TAG, "updateNotificationReminder: ${WorkManager.getInstance().getWorkInfosByTag(ReminderManager.OVERLAY_WORK).get()}")
    }

    fun cancelReminders() {
        cancelNotificationReminder()
        cancelOverlayReminder()
    }

    private fun cancelNotificationReminder() {
        prefs.disableNotificationReminder()
        Log.d(TAG, "cancelNotificationReminder: ${WorkManager.getInstance().getWorkInfosByTag(NOTIFICATION_WORK).get()}")
        WorkManager.getInstance().cancelAllWorkByTag(NOTIFICATION_WORK)
    }

    private fun cancelOverlayReminder() {
        prefs.disableOverlayReminder()
        Log.d(TAG, "cancelOverlayReminder: ${WorkManager.getInstance().getWorkInfosByTag(OVERLAY_WORK).get()}")
        WorkManager.getInstance().cancelAllWorkByTag(OVERLAY_WORK)
    }

    fun deleteNotification() {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(5360)
    }

    companion object {
        const val NOTIFICATION_WORK = "NotificationWork"
        const val OVERLAY_WORK = "OverlayWork"
    }
}