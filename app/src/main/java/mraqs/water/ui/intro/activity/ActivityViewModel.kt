package mraqs.water.ui.intro.activity

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.PreferenceManager
import mraqs.water.util.WaterAmount
import mraqs.water.util.WaterAmount.Gender
import javax.inject.Inject

class ActivityViewModel @Inject constructor(private val prefsManager: PreferenceManager) : ViewModel() {

    val time = ObservableField(2)
    val viewState = MutableLiveData<ViewState>()

    init {
        prefsManager.saveActivityTime(2)
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
        updateDailyWaterAmount()
        showNextScreen()
    }

    private fun updateActivityTime(newTime: Int) {
        prefsManager.saveActivityTime(newTime)
    }

    private fun updateDailyWaterAmount() {
        val dailyGoal = calculateDailyWaterAmount()
        prefsManager.saveGoal(dailyGoal)
    }

    private fun calculateDailyWaterAmount(): Int {
        val gender = Gender.MALE
        val weight = prefsManager.loadWeight()
        val activityTime = prefsManager.loadActivityTime()
        return WaterAmount.calculateWaterAmount(gender, weight, activityTime)
    }

    fun showNextScreen() {
        viewState.postValue(ViewState.NextScreen)
    }

    sealed class ViewState {
        object NextScreen : ViewState()
    }
}
