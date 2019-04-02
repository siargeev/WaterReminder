package mraqs.water.manager

import mraqs.notification.data.model.Gender
import mraqs.notification.data.model.User
import mraqs.notification.data.model.Water
import mraqs.notification.data.model.toGender
import mraqs.water.data.model.Preference
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

    fun saveWater(water: Water) {
        saveProgress(water.progress)
        saveGoal(water.goal)
        saveVolume(water.volume)
    }

    fun loadWater(): Water {
        val progress = loadProgress()
        val goal = loadWaterGoal()
        val volume = loadVolume()
        return Water(progress, goal, volume)
    }

    fun saveUser(user: User) {
        saveGender(user.gender)
        saveWeight(user.weight)
        saveActivityTime(user.activityTime)
    }

    fun loadUser(): User {
        val gender = loadGender()
        val weight = loadWeight()
        val activityTime = loadActivityTime()
        return User(gender, weight, activityTime)
    }

    private fun saveGender(gender: Gender) {
        prefs.put(Preference.USER_GENDER, gender.name)
    }

    private fun saveWeight(weight: Int) {
        prefs.put(Preference.USER_WEIGHT, weight)
    }

    private fun saveActivityTime(time: Int) {
        prefs.put(Preference.USER_ACTIVITY_TIME, time)
    }

    private fun saveGoal(waterAmount: Int) {
        prefs.put(Preference.USER_WATER_GOAL, waterAmount)
    }

    private fun saveVolume(volume: Int) {
        prefs.put(Preference.USER_VOLUME, volume)
    }

    private fun loadVolume(): Int {
        return prefs.getInt(Preference.USER_VOLUME)
    }

    private fun loadWaterGoal(): Int {
        return prefs.getInt(Preference.USER_WATER_GOAL)
    }

    private fun loadGender(): Gender {
        return prefs.getString(Preference.USER_GENDER)!!.toGender()
    }

    private fun loadWeight(): Int {
        return prefs.getInt(Preference.USER_WEIGHT)
    }

    private fun loadActivityTime(): Int {
        return prefs.getInt(Preference.USER_ACTIVITY_TIME)
    }

    private fun saveProgress(progress: Int) {
        prefs.put(Preference.USER_PROGRESS, progress)
    }

    private fun loadProgress(): Int {
        return prefs.getInt(Preference.USER_PROGRESS)
    }
}