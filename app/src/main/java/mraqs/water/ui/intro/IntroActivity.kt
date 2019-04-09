package mraqs.water.ui.intro

import android.os.Bundle
import android.view.View
import com.github.paolorotolo.appintro.AppIntro
import mraqs.water.R
import mraqs.water.ui.intro.activity.ActivityFragment
import mraqs.water.ui.intro.gender.GenderFragment
import mraqs.water.ui.intro.weight.WeightFragment
import mraqs.water.ui.warning.WarningFragment
import org.jetbrains.anko.backgroundColor

class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupIntro()
    }

    private fun setupIntro() {
        setSwipeLock(true)
        setNextPageSwipeLock(true)
        showSkipButton(false)
        showSeparator(false)
        showBackButtonWithDone = false
        isProgressButtonEnabled = false
        backButtonVisibilityWithDone = false
        setDoneText("")
        doneButton.visibility = View.GONE
        setNextArrowColor(resources.getColor(R.color.transparent))
        setIndicatorColor(resources.getColor(R.color.red), resources.getColor(R.color.blue))
        doneButton.backgroundColor = resources.getColor(R.color.transparent)
        addSlides()
    }

    private fun addSlides() {
        addSlide(WarningFragment.newInstance())
        addSlide(GenderFragment.newInstance())
        addSlide(WeightFragment.newInstance(null))
        addSlide(ActivityFragment.newInstance(null))
    }

    interface OnNextClickListener {
        fun onClickNext(activity: IntroActivity)
    }
}
