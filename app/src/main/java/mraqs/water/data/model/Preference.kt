package mraqs.water.data.model

import android.content.Context
import net.grandcentrix.tray.TrayPreferences
import net.grandcentrix.tray.core.TrayStorage
import javax.inject.Inject

class Preference @Inject constructor(context: Context) : TrayPreferences(context, "WaterReminderModule", 1, TrayStorage.Type.USER) {

    companion object {
        const val IS_FIRST_LAUNCH = "is_first_launch"
        const val SETTING_SHOW_NOTIFICATION = "settings_show_notification"
        const val SETTING_OVERLAY_REMINDER_ENABLED = "setting_overlay_reminder_enabled"
        const val SETTING_NOTIFICATION_REMINDER_ENABLED = "setting_notification_reminder_enabled"
        const val SETTING_DRUNK_GLASSES = "setting_drunk_glasses"
        const val SETTING_REMINDER_INTERVAL = "setting_reminder_interval"

        const val USER_GENDER = "user_gender"
        const val USER_WEIGHT = "user_weight"

        const val USER_ACTIVITY_TIME = "user_activity"
        const val USER_WATER_GOAL = "user_water_amount"
        const val USER_PROGRESS = "user_progress"

        const val USER_VOLUME = "user_volume"
    }
}