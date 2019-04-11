package mraqs.water.ui.main.settings

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.manager.ReminderManager
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.ActivityChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.IntervalChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.PrivacyPolicy
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.TermsOfUse
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.UnitsChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.UpdateTimeUnit
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.VolumeChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.WeightChooser
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val reminderManager: ReminderManager,
    private val prefs: LivePreferenceManager
) : ViewModel() {

    val viewState = MutableLiveData<ViewState>()
    private val TAG = "SettingsViewModel"

    var isNotificationsEnabled = ObservableField<Boolean>(prefs.isNotificationsEnabled())
    val weight = ObservableField<Int>(prefs.loadWeight().value!!)
    val activity = ObservableField<Int>(prefs.loadActivityTime().value!!)
    val volume = ObservableField<Int>(prefs.loadVolume().value!!)
    val interval = ObservableField<Int>(prefs.loadReminderInterval().value!!)
    val timeUnit = ObservableField<String>("min")

    init {
        prefs.loadWeight().observeForever { weight.set(it) }
        prefs.loadActivityTime().observeForever { activity.set(it) }
        prefs.loadVolume().observeForever { volume.set(it) }
        prefs.loadReminderInterval().observeForever { updateIntervalLabel(it) }
    }

    private fun updateIntervalLabel(newInterval: Int) {
        reminderManager.updateReminders()
        if (newInterval == 15 || newInterval == 30 || newInterval == 45) {
            updateTimeUnit("min")
            interval.set(newInterval)
        }
        if (newInterval == 60 || newInterval == 120 || newInterval == 180) {
            updateTimeUnit("hour")
            interval.set(newInterval.div(60))
        }
    }

    fun onNotificationsChecked(enabled: Boolean) {
        prefs.enableNotifications(enabled)
        if (enabled) {
            reminderManager.startReminders()
        } else {
            reminderManager.cancelReminders()
        }
    }

    fun showUnitsChooser() = viewState.postValue(UnitsChooser)
    fun showIntervalChooser() = viewState.postValue(IntervalChooser)
    fun showWeightChooser() = viewState.postValue(WeightChooser)
    fun showActivityChooser() = viewState.postValue(ActivityChooser)
    fun showVolumeChooser() = viewState.postValue(VolumeChooser)
    fun showPrivacyPolicy() = viewState.postValue(PrivacyPolicy)
    fun showTermsOfUse() = viewState.postValue(TermsOfUse)
    private fun updateTimeUnit(unit: String) = viewState.postValue(UpdateTimeUnit(unit))

    sealed class ViewState {
        object UnitsChooser : ViewState()
        object IntervalChooser : ViewState()
        object WeightChooser : ViewState()
        object ActivityChooser : ViewState()
        object VolumeChooser : ViewState()
        object PrivacyPolicy : ViewState()
        object TermsOfUse : ViewState()
        class UpdateTimeUnit(val unit: String) : ViewState()
    }
}

