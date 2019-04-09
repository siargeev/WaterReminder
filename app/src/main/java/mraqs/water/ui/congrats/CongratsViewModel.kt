package mraqs.water.ui.congrats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.ui.congrats.CongratsViewModel.ViewState.Loading
import javax.inject.Inject

class CongratsViewModel @Inject constructor() : ViewModel() {

    val viewState = MutableLiveData<ViewState>()

    fun startLoading() {
        viewState.postValue(Loading(true))
    }

    fun stopLoading() {
        viewState.postValue(Loading(false))
    }

    sealed class ViewState {
        class Loading(val loading: Boolean) : ViewState()
    }
}
