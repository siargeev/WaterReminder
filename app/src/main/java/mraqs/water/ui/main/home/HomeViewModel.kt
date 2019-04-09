package mraqs.water.ui.main.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.manager.LivePreferenceManager.Companion.toGender
import mraqs.water.manager.PermissionManager
import mraqs.water.manager.ReminderManager
import mraqs.water.util.WaterAmount
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    internal val prefs: LivePreferenceManager,
    internal val permissionManager: PermissionManager,
    internal val reminderManager: ReminderManager
) : ViewModel() {

    private val TAG = "HomeViewModel"
    val uiState = MutableLiveData<UIState>()
    val goal = ObservableField(prefs.loadGoal().value!!)
    val progress = ObservableField(prefs.loadProgress().value!!)
    val volume = ObservableField(prefs.loadVolume().value!!)

    init {
        prefs.loadGoal().observeForever { goal.set(it) }
        prefs.loadProgress().observeForever { progress.set(it); checkForGoalReaching() }
        prefs.loadVolume().observeForever { volume.set(it) }
        prefs.loadWeight().observeForever { updateGoal() }
        prefs.loadActivityTime().observeForever { updateGoal() }
        startNotificationReminder()
        startOverlayReminder()
        removeNotification()
    }

    private fun startOverlayReminder() {
//        if (permissionManager.isNotIgnoreBatteryOptimization) requestBatteryPermission()
        if (permissionManager.isForbiddenToDrawOverlay) {
            requestOverlayPermission()
        } else {
            if (prefs.isNotificationsEnabled()) {
                startOverlay()
            }
        }
    }

    fun startOverlay() {
        if (prefs.isNotificationsEnabled()) {
            reminderManager.startOverlayReminder()
        }
    }

    private fun startNotificationReminder() {
        if (prefs.isNotificationsEnabled()) {
            reminderManager.startNotificationReminder()
        }
    }

    private fun removeNotification() {
        reminderManager.deleteNotification()
    }

    private fun checkForGoalReaching() {
        if (isGoalReached()) {
            registerGoalReaching()
            showCongrats()
        }
    }

    private fun isGoalReached(): Boolean {
        return progress.get()!! >= goal.get()!!
    }

    private fun registerGoalReaching() {
        progress.set(0)
        prefs.saveProgress(0)
    }

    fun onDrinkClick() {
        updateProgress()
        prefs.addDrunkGlass()
        checkForTimeTo { showInterstitial() }
    }

    private fun checkForTimeTo(block: () -> Unit) {
        if (prefs.getDrunkGlasses().rem(3) == 0)
            block()
    }

    private fun updateGoal() {
        val dailyGoal = calculateDailyWaterAmount()
        prefs.saveGoal(dailyGoal)
    }

    private fun calculateDailyWaterAmount(): Int {
        val gender = prefs.loadGender().value!!.toGender()
        val weight = prefs.loadWeight().value!!
        val activityTime = prefs.loadActivityTime().value!!
        return WaterAmount.calculateWaterAmount(gender, weight, activityTime)
    }

    private fun updateProgress() {
        val newProgress = progress.get()!! + volume.get()!!
        progress.set(newProgress)
        prefs.saveProgress(newProgress)
    }

    private fun showCongrats() = uiState.postValue(UIState.Congratulations)
    fun showShowcase() = uiState.postValue(UIState.Showcase)
    fun requestOverlayPermission() = uiState.postValue(UIState.RequestOverlayPermission)
    fun requestBatteryPermission() = uiState.postValue(UIState.RequestBatteryPermission)
    fun showInterstitial() = uiState.postValue(UIState.ShowInterstitial)

    sealed class UIState {
        object Showcase : UIState()
        object RequestOverlayPermission : UIState()
        object RequestBatteryPermission : UIState()
        object ShowInterstitial : UIState()
        object Congratulations : UIState()
    }
}