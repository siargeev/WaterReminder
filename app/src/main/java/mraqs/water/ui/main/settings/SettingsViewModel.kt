package mraqs.water.ui.main.settings

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.manager.ReminderManager
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.ActivityChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.UnitsChooser
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

    init {
        prefs.loadWeight().observeForever { weight.set(it) }
        prefs.loadActivityTime().observeForever { activity.set(it) }
        prefs.loadVolume().observeForever { volume.set(it) }
    }

    fun onNotificationsChecked(enabled: Boolean) {
        prefs.enableNotifications(enabled)
        if (enabled) {
            reminderManager.startNotificationReminder()
            reminderManager.startOverlayReminder()
        } else {
            reminderManager.cancelReminders()
        }
    }

    fun showUnitsChooser() = viewState.postValue(UnitsChooser)
    fun showWeightChooser() = viewState.postValue(WeightChooser)
    fun showActivityChooser() = viewState.postValue(ActivityChooser)
    fun showVolumeChooser() = viewState.postValue(VolumeChooser)

    sealed class ViewState {
        object UnitsChooser : ViewState()
        object WeightChooser : ViewState()
        object ActivityChooser : ViewState()
        object VolumeChooser : ViewState()
    }
}

