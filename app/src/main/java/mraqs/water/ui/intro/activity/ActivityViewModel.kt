package mraqs.water.ui.intro.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import mraqs.water.util.decrement
import mraqs.water.util.increment

class ActivityViewModel : ViewModel() {
    val time: MutableLiveData<Int> by lazy { MutableLiveData<Int>().apply { value = 2 } }

    fun onPlusClick() {
        if (time.value!! < 12)
            time.increment()
    }

    fun onMinusClick() {
        if (time.value!! > 0)
            time.decrement()
    }
}
