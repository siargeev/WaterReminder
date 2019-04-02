package mraqs.water.ui.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import mraqs.water.ui.intro.activity.ActivityFragment
import mraqs.water.ui.intro.gender.GenderFragment
import mraqs.water.ui.intro.weight.WeightFragment
import mraqs.water.ui.main.home.HomeActivity
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.startActivity

class IntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupIntro()
    }

    private fun setupIntro() {
        addSlides()
        showSkipButton(false)
        isProgressButtonEnabled = true
        doneButton.backgroundColor = android.R.color.transparent
    }

    private fun addSlides() {
//        addSlide(WarningFragment.newInstance())
        addSlide(GenderFragment.newInstance())
        addSlide(WeightFragment.newInstance())
        addSlide(ActivityFragment.newInstance())
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        startActivity<HomeActivity>()
        finish()
    }
}
