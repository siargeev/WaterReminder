package mraqs.water.util

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Int>.decrement() {
    val current = value
    postValue(current!! - 1)
}

fun MutableLiveData<Int>.increment() {
    val current = value
    postValue(current!! + 1)
}