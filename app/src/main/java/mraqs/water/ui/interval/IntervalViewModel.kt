package mraqs.water.ui.interval

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.ui.interval.IntervalViewModel.UIState.SaveClick
import javax.inject.Inject

class IntervalViewModel @Inject constructor(private val prefs: LivePreferenceManager) : ViewModel() {

    private val TAG = "IntervalViewModel"

    val uiState = MutableLiveData<UIState>()

    val interval = MutableLiveData<String>()
    val intervals = listOf("15", "30", "45", "1", "2", "3").toTypedArray()

    init {
        interval.postValue("15")
    }

    fun onSaveClick() {
        updateInterval(interval.value!!)
        saveClick()
    }

    private fun updateInterval(interval: String) {
        val newInterval = when (interval.toInt()) {
            1 -> 60
            2 -> 120
            3 -> 180
            15 -> 15
            30 -> 30
            45 -> 45
            else -> 15
        }
        prefs.updateReminderInterval(newInterval)
    }

    fun saveClick() = uiState.postValue(SaveClick)

    sealed class UIState {
        object SaveClick : UIState()
    }
}
