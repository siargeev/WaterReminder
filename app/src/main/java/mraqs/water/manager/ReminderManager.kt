package mraqs.water.manager

import android.app.NotificationManager
import android.app.job.JobScheduler
import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.ExistingPeriodicWorkPolicy.REPLACE
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import mraqs.water.notification.worker.NotificationWorker
import mraqs.water.notification.worker.NotificationWorker.Companion.NOTIFICATION_ID
import mraqs.water.notification.worker.OverlayWorker
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MINUTES
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderManager @Inject constructor(private var applicationContext: Context, private val prefs: LivePreferenceManager) {


    private val TAG = "ReminderManager"

    fun startReminders() {
        if (prefs.isNotificationsEnabled()) {
            if (prefs.isNotificationReminderDisabled())
                startNotificationReminder()
            if (prefs.isOverlayReminderDisabled())
                startOverlayReminder()
        }
    }

    fun updateReminders() {
        if (prefs.isNotificationsEnabled()) {
            updateNotificationReminderInterval()
            updateOverlayReminderInterval()
        }
    }

    private fun startNotificationReminder() {
        prefs.enableNotificationReminder()
        val reminderInterval = prefs.loadReminderInterval().value!!.toLong()
        val notificationRequest = PeriodicWorkRequest
            .Builder(NotificationWorker::class.java, reminderInterval, TimeUnit.MINUTES, reminderInterval - 1, MINUTES)
            .addTag(NOTIFICATION_WORK)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(NOTIFICATION_WORK, KEEP, notificationRequest)
        Log.d(TAG, "startNotificationReminder: $reminderInterval")
    }

    private fun updateNotificationReminderInterval() {
        val newInterval = prefs.loadReminderInterval().value!!.toLong()
        val newNotificationRequest = PeriodicWorkRequest
            .Builder(NotificationWorker::class.java, newInterval, MINUTES, newInterval - 1, MINUTES)
            .addTag(NOTIFICATION_WORK)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(NOTIFICATION_WORK, REPLACE, newNotificationRequest)
        Log.d(TAG, "updateNotificationReminder: $newInterval")
    }

    private fun startOverlayReminder() {
        prefs.enableOverlayReminder()
        deleteOverlay()
        val reminderInterval = prefs.loadReminderInterval().value!!.toLong()
        val overlayRequest = PeriodicWorkRequest
            .Builder(OverlayWorker::class.java, reminderInterval, TimeUnit.MINUTES, reminderInterval - 1, MINUTES)
            .addTag(OVERLAY_WORK)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(OVERLAY_WORK, KEEP, overlayRequest)
        Log.d(TAG, "startOverlayReminder: $reminderInterval")
    }

    private fun updateOverlayReminderInterval() {
        deleteOverlay()
        val newInterval = prefs.loadReminderInterval().value!!.toLong()
        val newOverlayRequest = PeriodicWorkRequest
            .Builder(OverlayWorker::class.java, newInterval, MINUTES, newInterval - 1, MINUTES)
            .addTag(ReminderManager.OVERLAY_WORK)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(ReminderManager.OVERLAY_WORK, REPLACE, newOverlayRequest)
        Log.d(TAG, "updateOverlayReminder: $newInterval")
    }

    fun cancelReminders() {
        cancelNotificationReminder()
        cancelOverlayReminder()
    }

    private fun cancelNotificationReminder() {
        prefs.disableNotificationReminder()
        Log.d(TAG, "cancelNotificationReminder")
        WorkManager.getInstance().cancelAllWorkByTag(NOTIFICATION_WORK)
    }

    private fun cancelOverlayReminder() {
        prefs.disableOverlayReminder()
        Log.d(TAG, "cancelOverlayReminder")
        WorkManager.getInstance().cancelAllWorkByTag(OVERLAY_WORK)
    }

    fun deleteOverlay() {
        val jobScheduler = applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(OverlayWorker.OVERLAY_JOB_ID)
    }

    fun deleteNotification() {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(NOTIFICATION_ID)
    }

    companion object {
        const val NOTIFICATION_WORK = "NotificationWork"
        const val OVERLAY_WORK = "OverlayWork"
    }
}