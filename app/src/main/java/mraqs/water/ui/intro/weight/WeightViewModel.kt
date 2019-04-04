package mraqs.water.ui.intro.weight

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.PreferenceManager
import javax.inject.Inject

class WeightViewModel @Inject constructor(private val prefManager: PreferenceManager) : ViewModel() {

    private val TAG = "WeightViewModel"
    val weight = ObservableField(55)
    val viewState = MutableLiveData<ViewState>()

    init {
        prefManager.saveWeight(55)
    }

    fun onPlusClick() {
        if (weight.get()!! < 140)
            weight.set((weight.get()!!) + 1)
    }

    fun onMinusClick() {
        if (weight.get()!! > 20)
            weight.set((weight.get()!!) - 1)
    }

    fun onNextButtonClick() {
        updateWeight(weight.get()!!)
        showNextScreen()
    }

    private fun updateWeight(newWeight: Int) {
        prefManager.saveWeight(newWeight)
    }

    private fun showNextScreen() {
        viewState.postValue(ViewState.NextScreen)
    }

    sealed class ViewState {
        object NextScreen : ViewState()
    }
}

