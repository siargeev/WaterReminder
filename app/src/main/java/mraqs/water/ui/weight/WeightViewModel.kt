package mraqs.water.ui.weight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.util.WeightUnit
import mraqs.water.util.WeightUnit.KG
import mraqs.water.util.WeightUnit.LBS
import mraqs.water.util.toWeight
import javax.inject.Inject

class WeightViewModel @Inject constructor(private val prefs: LivePreferenceManager) : ViewModel() {

    private val TAG = "WeightViewModel"
    val viewState = MutableLiveData<ViewState>()
    var weight = 55
    val unit = MutableLiveData<WeightUnit>()

    private val weightsInKG = (20..140).toList().toIntArray().map { it.toString() }.toTypedArray()
    private val weightsInLBS = (44..308).toList().toIntArray().map { it.toString() }.toTypedArray()
    val units = listOf("kg", "lbs").toTypedArray()

    init {
        unit.postValue(KG)
    }

    fun onNextButtonClick() {
        saveWeight(weight)
        showNextScreen()
    }

    private fun saveWeight(newWeight: Int) {
        prefs.saveWeight(newWeight)
    }

    fun updateWeight(newWeight: Int) {
        weight = newWeight.toWeight(unit.value!!)
    }

    fun updateUnit(newUnit: WeightUnit) {
        unit.postValue(newUnit)
    }

    fun provideWeights(unit: WeightUnit = KG): Array<String> {
        return when (unit) {
            KG -> weightsInKG
            LBS -> weightsInLBS
        }
    }

    private fun showNextScreen() {
        viewState.postValue(ViewState.NextScreen)
    }

    sealed class ViewState {
        object NextScreen : ViewState()
    }
}

