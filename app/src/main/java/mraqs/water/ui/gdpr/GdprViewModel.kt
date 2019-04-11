package mraqs.water.ui.gdpr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.ads.consent.ConsentStatus
import mraqs.water.manager.PermissionManager
import mraqs.water.ui.gdpr.GdprViewModel.UIState.OnNextClick
import mraqs.water.ui.gdpr.GdprViewModel.UIState.ShowWarning
import javax.inject.Inject

class GdprViewModel @Inject constructor(private val permissionManager: PermissionManager) : ViewModel() {

    val uiState = MutableLiveData<UIState>()

    fun onClick(status: ConsentStatus) {
        requestPermissions()
        if (permissionManager.isForbiddenToDrawOverlay || permissionManager.isNotIgnoreBatteryOptimization)
            showWarning()
        else
            showNextScreen(status)
    }

    private fun requestPermissions() {
        permissionManager.requestPermissions()
    }

    private fun showNextScreen(status: ConsentStatus) = uiState.postValue(OnNextClick(status))
    private fun showWarning() = uiState.postValue(ShowWarning)

    sealed class UIState {
        class OnNextClick(val status: ConsentStatus) : UIState()
        object ShowWarning : UIState()
    }
}