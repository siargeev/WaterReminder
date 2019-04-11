package mraqs.water.ui.main.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.manager.LivePreferenceManager.Companion.toGender
import mraqs.water.manager.ReminderManager
import mraqs.water.util.WaterAmount
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val prefs: LivePreferenceManager,
    private val reminderManager: ReminderManager
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
        private const val INTERSTITIAL_SHOWING_INTERVAL = 3
    }

    val uiState = MutableLiveData<UIState>()
    val goal = ObservableField(prefs.loadGoal().value!!)
    val progress = ObservableField(prefs.loadProgress().value!!)
    val volume = ObservableField(prefs.loadVolume().value!!)

    init {
        observePreferences()
        setupReminder()
        showFirstInfo()
        prefs.registerFirstLaunch()
    }

    private fun showFirstInfo() {
        if (prefs.isFirstLaunch())
            setStateToFirstInfo()
    }

    private fun observePreferences() {
        prefs.loadGoal().observeForever { goal.set(it) }
        prefs.loadProgress().observeForever { progress.set(it); checkForGoalReaching() }
        prefs.loadVolume().observeForever { volume.set(it) }
        prefs.loadWeight().observeForever { updateGoal() }
        prefs.loadActivityTime().observeForever { updateGoal() }
    }

    private fun setupReminder() {
        reminderManager.startReminders()
        reminderManager.deleteNotification()
    }

    private fun checkForGoalReaching() {
        if (isGoalReached() && prefs.loadGoal().value!! != 0) {
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
        checkForTimeToShowInterstitial()
    }

    private fun checkForTimeToShowInterstitial() {
        if (prefs.getDrunkGlasses().rem(INTERSTITIAL_SHOWING_INTERVAL) == 0)
            showInterstitial()
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
    private fun showShowcase() = uiState.postValue(UIState.Showcase)
    private fun showInterstitial() = uiState.postValue(UIState.ShowInterstitial)
    private fun setStateToFirstInfo() = uiState.postValue(UIState.FirstInfo)

    sealed class UIState {
        object Showcase : UIState()
        object ShowInterstitial : UIState()
        object Congratulations : UIState()
        object FirstInfo : UIState()
    }
}