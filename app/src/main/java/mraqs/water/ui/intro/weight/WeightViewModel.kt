package mraqs.water.ui.intro.weight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mraqs.water.util.decrement
import mraqs.water.util.increment

class WeightViewModel : ViewModel() {

    val weight: MutableLiveData<Int> by lazy { MutableLiveData<Int>().apply { value = 55 } }

    fun onPlusClick() {
        if (weight.value!! < 140)
            weight.increment()
    }

    fun onMinusClick() {
        if (weight.value!! > 20)
            weight.decrement()
    }
}

