package mraqs.water.util

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

fun AdView.show() {
    loadAd(AdRequest.Builder().build())
//    loadAd(AdRequest.Builder().addTestDevice("165F540DCF8AC9F030300B00EFCD1FF3").build())
}

