package mraqs.water.ui.main.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.PreferenceManager
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val prefManager: PreferenceManager) : ViewModel() {
    private val TAG = "HomeViewModel"
    val dailyGoal = ObservableField(0)
    val progress = ObservableField(0)
    val volume = MutableLiveData<Int>()

    init {
        loadDailyGoal()
        loadProgress()
        loadVolume()
    }

    private fun loadProgress() {
        val currentProgress = prefManager.loadProgress()
        progress.set(currentProgress)
    }

    private fun loadDailyGoal() {
        val goal = prefManager.loadWaterGoal()
        dailyGoal.set(goal)
    }

    private fun loadVolume() {
        volume.postValue(prefManager.loadVolume())
    }

    fun onDrinkClick() {
        updateProgress()
    }

    private fun updateProgress() {
        Log.d(TAG, "updateProgress: ${(progress.get()!!) + volume.value!!}")
        progress.set((progress.get()!!) + volume.value!!)
    }
}