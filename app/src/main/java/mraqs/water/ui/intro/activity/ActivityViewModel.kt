package mraqs.water.ui.intro.activity

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {
    val time = ObservableField(2)

    fun onPlusClick() {
        if (time.get()!! < 12)
            time.set((time.get()!!) + 1)
    }

    fun onMinusClick() {
        if (time.get()!! > 0)
            time.set((time.get()!!) - 1)
    }
}
