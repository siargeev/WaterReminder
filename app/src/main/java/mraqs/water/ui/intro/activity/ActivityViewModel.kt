package mraqs.water.ui.intro.activity

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import javax.inject.Inject

class ActivityViewModel @Inject constructor(private val prefs: LivePreferenceManager) : ViewModel() {

    val time = ObservableField(2)
    val viewState = MutableLiveData<ViewState>()

    init {
//        prefsManager.saveActivityTime(2)
    }

    fun onPlusClick() {
        if (time.get()!! < 12)
            time.set((time.get()!!) + 1)
    }

    fun onMinusClick() {
        if (time.get()!! > 0)
            time.set((time.get()!!) - 1)
    }

    fun onNextButtonClick() {
        updateActivityTime(time.get()!!)
        showNextScreen()
    }

    private fun updateActivityTime(newTime: Int) {
        prefs.saveActivityTime(newTime)
    }

    private fun showNextScreen() {
        viewState.postValue(ViewState.NextScreen)
    }

    sealed class ViewState {
        object NextScreen : ViewState()
    }
}
