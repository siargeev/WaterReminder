package mraqs.water.ui.volume

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import mraqs.water.manager.LivePreferenceManager
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnBottleClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnCupClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnGlassClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnSaveClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnSmallBottleClick
import mraqs.water.ui.volume.VolumeViewModel.UIState.OnSmallGlassClick
import javax.inject.Inject

class VolumeViewModel @Inject constructor(private val prefs: LivePreferenceManager) : ViewModel() {

    val uiState = MutableLiveData<UIState>()

    init {
        val volume = prefs.loadVolume().value!!
        when (volume) {
            100 -> selectSmallGlass()
            250 -> selectCup()
            300 -> selectGlass()
            500 -> selectSmallBottle()
            1000 -> selectBottle()
        }
    }

    private fun updateVolume(volume: Int) = prefs.saveVolume(volume)

    fun onSmallGlassClick() {
        updateVolume(100)
        selectSmallGlass()
    }

    fun onCupClick() {
        updateVolume(250)
        selectCup()
    }

    fun onGlassClick() {
        updateVolume(300)
        selectGlass()
    }

    fun onSmallBottleClick() {
        updateVolume(500)
        selectSmallBottle()
    }

    fun onBottleClick() {
        updateVolume(1000)
        selectBottle()
    }

    private fun selectSmallGlass() = uiState.postValue(OnSmallGlassClick)
    private fun selectCup() = uiState.postValue(OnCupClick)
    private fun selectGlass() = uiState.postValue(OnGlassClick)
    private fun selectSmallBottle() = uiState.postValue(OnSmallBottleClick)
    private fun selectBottle() = uiState.postValue(OnBottleClick)
    fun onSaveClick() = uiState.postValue(OnSaveClick)

    sealed class UIState {
        object OnSmallGlassClick : UIState()
        object OnCupClick : UIState()
        object OnGlassClick : UIState()
        object OnSmallBottleClick : UIState()
        object OnBottleClick : UIState()
        object OnSaveClick : UIState()
    }
}
