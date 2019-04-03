package mraqs.water.manager

import mraqs.water.data.model.Preference
import mraqs.water.util.WaterAmount
import mraqs.water.util.WaterAmount.Gender
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(private var prefs: Preference) {


    fun getReminderInterval(): Long {
        return prefs.getLong(Preference.SETTING_REMINDER_INTERVAL, 15)
    }

    fun updateReminderInterval(newInterval: Long) {
        prefs.put(Preference.SETTING_REMINDER_INTERVAL, newInterval)
    }

    fun getDrunkGlasses(): Int {
        return prefs.getInt(Preference.SETTING_DRUNK_GLASSES, 0)
    }

    fun addDrunkGlass() {
        val num = getDrunkGlasses()
        prefs.put(Preference.SETTING_DRUNK_GLASSES, num + 1)
    }

    fun enableNotifications(enabled: Boolean) {
        prefs.put(Preference.SETTING_SHOW_NOTIFICATION, enabled)
    }

    fun isNotificationsEnabled(): Boolean {
        return if (prefs.contains(Preference.SETTING_SHOW_NOTIFICATION)) prefs.getBoolean(Preference.SETTING_SHOW_NOTIFICATION)
        else true
    }

    fun enableOverlayReminder() {
        prefs.put(Preference.SETTING_OVERLAY_REMINDER_ENABLED, true)
    }

    fun disableOverlayReminder() {
        prefs.remove(Preference.SETTING_OVERLAY_REMINDER_ENABLED)
    }

    fun enableNotificationReminder() {
        prefs.put(Preference.SETTING_NOTIFICATION_REMINDER_ENABLED, true)
    }

    fun disableNotificationReminder() {
        prefs.remove(Preference.SETTING_NOTIFICATION_REMINDER_ENABLED)
    }

    fun isNotificationReminderDisabled(): Boolean {
        return !prefs.contains(Preference.SETTING_NOTIFICATION_REMINDER_ENABLED)
    }

    fun isOverlayReminderDisabled(): Boolean {
        return !prefs.contains(Preference.SETTING_OVERLAY_REMINDER_ENABLED)
    }

    fun registerFirstLaunch() {
        prefs.put(Preference.IS_FIRST_LAUNCH, true)
    }

    fun isFirstLaunch(): Boolean {
        return !prefs.contains(Preference.IS_FIRST_LAUNCH)
    }

    internal fun saveGender(gender: Gender) {
        prefs.put(Preference.USER_GENDER, gender.name)
    }

    internal fun saveWeight(weight: Int) {
        prefs.put(Preference.USER_WEIGHT, weight)
    }

    internal fun saveActivityTime(time: Int) {
        prefs.put(Preference.USER_ACTIVITY_TIME, time)
    }

    internal fun saveGoal(waterAmount: Int) {
        prefs.put(Preference.USER_WATER_GOAL, waterAmount)
    }

    internal fun saveVolume(volume: Int) {
        prefs.put(Preference.USER_VOLUME, volume)
    }

    internal fun loadVolume(): Int {
        return prefs.getInt(Preference.USER_VOLUME)
    }

    internal fun loadWaterGoal(): Int {
        return prefs.getInt(Preference.USER_WATER_GOAL)
    }

    internal fun loadGender(): Gender {
        return prefs.getString(Preference.USER_GENDER)!!.toGender()
    }

    internal fun loadWeight(): Int {
        return prefs.getInt(Preference.USER_WEIGHT)
    }

    internal fun loadActivityTime(): Int {
        return prefs.getInt(Preference.USER_ACTIVITY_TIME)
    }

    internal fun saveProgress(progress: Int) {
        prefs.put(Preference.USER_PROGRESS, progress)
    }

    internal fun loadProgress(): Int {
        return prefs.getInt(Preference.USER_PROGRESS)
    }

    fun String.toGender(): WaterAmount.Gender {
        return when (this.toUpperCase()) {
            "MALE" -> WaterAmount.Gender.MALE
            "FEMALE" -> WaterAmount.Gender.FEMALE
            else -> WaterAmount.Gender.MALE
        }
    }
}