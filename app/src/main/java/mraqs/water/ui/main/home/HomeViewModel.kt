package mraqs.water.ui.main.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.manager.LivePreferenceManager.Companion.toGender
import mraqs.water.util.WaterAmount
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val prefs: LivePreferenceManager) : ViewModel() {
    private val TAG = "HomeViewModel"
    val goal = ObservableField(prefs.loadGoal().value!!)
    val progress = ObservableField(prefs.loadProgress().value!!)
    val volume = ObservableField(prefs.loadVolume().value!!)

    init {
        if (prefs.isFirstLaunch())
            updateGoal()

        prefs.loadGoal().observeForever { goal.set(it) }
        prefs.loadProgress().observeForever { progress.set(it) }
        prefs.loadVolume().observeForever { volume.set(it) }

        prefs.loadWeight().observeForever { updateGoal() }
        prefs.loadActivityTime().observeForever { updateGoal() }
    }

    fun onDrinkClick() {
        updateProgress()
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
}