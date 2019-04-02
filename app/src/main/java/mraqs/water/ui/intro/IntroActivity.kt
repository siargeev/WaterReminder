package mraqs.water.ui.intro

import android.os.Bundle
import android.view.View
import com.github.paolorotolo.appintro.AppIntro
import mraqs.water.R
import mraqs.water.ui.intro.activity.ActivityFragment
import mraqs.water.ui.intro.gender.GenderFragment
import mraqs.water.ui.intro.weight.WeightFragment
import org.jetbrains.anko.backgroundColor

class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupIntro()
    }

    private fun setupIntro() {
        addSlides()
        showSkipButton(false)
        showBackButtonWithDone = false
        isProgressButtonEnabled = true
        backButtonVisibilityWithDone = false
        setDoneText("")
        doneButton.visibility = View.GONE
        setNextArrowColor(resources.getColor(R.color.transparent))
        setSeparatorColor(resources.getColor(R.color.transparent))
        setIndicatorColor(resources.getColor(R.color.red), resources.getColor(R.color.blue))
        doneButton.backgroundColor = resources.getColor(R.color.transparent)
    }

    private fun addSlides() {
        //todo add warning slide
//        addSlide(WarningFragment.newInstance())
        addSlide(GenderFragment.newInstance())
        addSlide(WeightFragment.newInstance())
        addSlide(ActivityFragment.newInstance())
    }
}
