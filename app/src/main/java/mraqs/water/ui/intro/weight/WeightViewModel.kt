package mraqs.water.ui.intro.weight

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class WeightViewModel : ViewModel() {

    val weight = ObservableField(55)

    fun onPlusClick() {
        if (weight.get()!! < 140)
            weight.set((weight.get()!!) + 1)
    }

    fun onMinusClick() {
        if (weight.get()!! > 20)
            weight.set((weight.get()!!) - 1)
    }
}

