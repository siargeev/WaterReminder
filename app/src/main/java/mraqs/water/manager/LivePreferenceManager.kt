package mraqs.water.manager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import me.ibrahimsn.library.LiveSharedPreferences
import mraqs.water.util.WaterAmount
import mraqs.water.util.WaterAmount.Gender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LivePreferenceManager @Inject constructor(context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val prefs = LiveSharedPreferences(sharedPreferences)

    fun loadWeight() = prefs.getInt(USER_WEIGHT, 0).apply { Log.d(TAG, "loadWeight:${this.value!!}") }
    fun loadActivityTime() = prefs.getInt(USER_ACTIVITY_TIME, 0).apply { Log.d(TAG, "loadActivityTime: ${this.value!!}") }
    fun loadGender() = prefs.getString(USER_GENDER, "MALE").apply { Log.d(TAG, "loadGender: ${this.value!!}") }
    fun loadVolume() = prefs.getInt(USER_VOLUME, 300).apply { Log.d(TAG, "loadVolume: ${this.value!!}") }
    fun loadGoal() = prefs.getInt(USER_GOAL, 0).apply { Log.d(TAG, "loadGoal: ${this.value!!}") }
    fun loadProgress() = prefs.getInt(USER_PROGRESS, 0).apply { Log.d(TAG, "loadProgress: ${this.value!!}") }

    fun saveWeight(weight: Int) = edit { it.putInt(USER_WEIGHT, weight) }.apply { Log.d(TAG, "saveWeight: $weight") }
    fun saveActivityTime(time: Int) = edit { it.putInt(USER_ACTIVITY_TIME, time) }.apply { Log.d(TAG, "saveActivityTime: $time") }
    fun saveGender(gender: Gender) = edit { it.putString(USER_GENDER, gender.toString()) }.apply { Log.d(TAG, "saveGender: $gender") }
    fun saveVolume(volume: Int) = edit { it.putInt(USER_VOLUME, volume) }.apply { Log.d(TAG, "saveVolume: $volume") }
    fun saveGoal(goal: Int) = edit { it.putInt(USER_GOAL, goal) }.apply { Log.d(TAG, "saveGoal: $goal") }
    fun saveProgress(progress: Int) = edit { it.putInt(USER_PROGRESS, progress) }.apply { Log.d(TAG, "saveProgress: $progress") }

    fun isFirstLaunch(): Boolean = !sharedPreferences.contains(IS_FIRST_LAUNCH)
    fun registerFirstLaunch() = edit { it.putBoolean(IS_FIRST_LAUNCH, true) }

    fun isNotificationsEnabled() = sharedPreferences.getBoolean(SETTING_NOTIFICATION_ENABLED, true)
    fun enableNotifications(enabled: Boolean) = edit { it.putBoolean(SETTING_NOTIFICATION_ENABLED, enabled) }

    fun enableNotificationReminder() = edit { it.putBoolean(SETTING_NOTIFICATION_REMINDER_ENABLED, true) }
    fun disableNotificationReminder() = edit { it.remove(SETTING_NOTIFICATION_REMINDER_ENABLED) }
    fun isNotificationReminderDisabled() = !sharedPreferences.contains(SETTING_NOTIFICATION_REMINDER_ENABLED)

    fun enableOverlayReminder() = edit { it.putBoolean(SETTING_OVERLAY_REMINDER_ENABLED, true) }
    fun disableOverlayReminder() = edit { it.remove(SETTING_OVERLAY_REMINDER_ENABLED) }
    fun isOverlayReminderDisabled() = !sharedPreferences.contains(SETTING_OVERLAY_REMINDER_ENABLED)

    fun getDrunkGlasses() = sharedPreferences.getInt(SETTING_DRUNK_GLASSES, 0)
    fun addDrunkGlass() {
        val num = getDrunkGlasses()
        edit { it.putInt(SETTING_DRUNK_GLASSES, num + 1) }
    }

    companion object {
        private const val TAG = "LivePreferenceManager"
        const val IS_FIRST_LAUNCH = "is_first_launch"
        const val SETTING_NOTIFICATION_ENABLED = "settings_show_notification"
        const val SETTING_OVERLAY_REMINDER_ENABLED = "setting_overlay_reminder_enabled"
        const val SETTING_NOTIFICATION_REMINDER_ENABLED = "setting_notification_reminder_enabled"
        const val SETTING_DRUNK_GLASSES = "setting_drunk_glasses"

        const val USER_GENDER = "user_gender"
        const val USER_WEIGHT = "user_weight"
        const val USER_ACTIVITY_TIME = "user_activity"

        const val USER_GOAL = "user_water_amount"
        const val USER_PROGRESS = "user_progress"
        const val USER_VOLUME = "user_volume"

        fun String.toGender(): WaterAmount.Gender {
            return when (this.toUpperCase()) {
                "MALE" -> WaterAmount.Gender.MALE
                "FEMALE" -> WaterAmount.Gender.FEMALE
                else -> WaterAmount.Gender.MALE
            }
        }
    }

    private fun edit(block: (SharedPreferences.Editor) -> Unit) {
        sharedPreferences.edit().apply { block(this) }.apply()
    }
}