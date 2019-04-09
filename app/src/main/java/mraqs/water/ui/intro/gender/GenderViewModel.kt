package mraqs.water.ui.intro.gender

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.ui.intro.gender.GenderViewModel.ViewState.FemaleActive
import mraqs.water.ui.intro.gender.GenderViewModel.ViewState.MaleActive
import mraqs.water.ui.intro.gender.GenderViewModel.ViewState.NextScreen
import mraqs.water.util.WaterAmount.Gender
import mraqs.water.util.WaterAmount.Gender.FEMALE
import mraqs.water.util.WaterAmount.Gender.MALE
import javax.inject.Inject

class GenderViewModel @Inject constructor(private val prefManager: LivePreferenceManager) : ViewModel() {
    val viewState = MutableLiveData<ViewState>()
    val gender = MutableLiveData<Gender>()

    fun onNextButtonClick() {
        if (gender.value != null) {
            prefManager.saveGender(gender.value!!)
            showNextScreen()
        }
    }

    private fun showNextScreen() {
        viewState.postValue(NextScreen)
    }

    fun onMaleClick() {
        viewState.postValue(MaleActive)
        gender.postValue(MALE)
    }

    fun onFemaleClick() {
        viewState.postValue(FemaleActive)
        gender.postValue(FEMALE)
    }

    sealed class ViewState {
        object NextScreen : ViewState()
        object MaleActive : ViewState()
        object FemaleActive : ViewState()
    }
}
