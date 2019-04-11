package mraqs.water.binding

import androidx.databinding.BindingAdapter
import com.github.lzyzsd.circleprogress.ArcProgress

class BindingAdapter {

    companion object {
        private val TAG = "BindingAdapter"

        @JvmStatic
        @BindingAdapter("custom:progress")
        fun ArcProgress.updateProgress(progress: Int) {
            setProgress(progress)
        }

        @JvmStatic
        @BindingAdapter("custom:max")
        fun ArcProgress.updateMax(max: Int) {
            setMax(max)
        }
    }
}