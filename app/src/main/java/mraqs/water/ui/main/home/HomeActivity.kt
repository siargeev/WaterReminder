package mraqs.water.ui.main.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest.Builder
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_home.adView
import mraqs.water.R
import mraqs.water.databinding.ActivityHomeBinding
import mraqs.water.ui.congrats.CongratsFragment
import mraqs.water.ui.firstinfo.FirstShowDialog
import mraqs.water.ui.main.BaseActivity
import mraqs.water.ui.main.home.HomeViewModel.UIState
import mraqs.water.ui.main.home.HomeViewModel.UIState.Congratulations
import mraqs.water.ui.main.home.HomeViewModel.UIState.FirstInfo
import mraqs.water.ui.main.home.HomeViewModel.UIState.ShowInterstitial
import mraqs.water.ui.main.home.HomeViewModel.UIState.Showcase
import mraqs.water.util.show
import javax.inject.Inject

class HomeActivity : BaseActivity(0) {

    companion object {
        private val TAG = "HomeActivity"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java) }
    private lateinit var binding: ActivityHomeBinding
    private lateinit var interstitial: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadInterstitial()
        setupBinding()
        setupBottomNavigation()
        observeUIState()
        adView.show()
    }

    private fun updateUIState(state: UIState) = when (state) {
        is FirstInfo -> showFirstInfo()
        is Showcase -> startShowcase()
        is ShowInterstitial -> showInterstitial()
        is Congratulations -> showCongratulations()
    }

    private fun showFirstInfo() {
        FirstShowDialog().show(supportFragmentManager, "")
    }

    private fun startShowcase() {
        /*Handler().post {
            val config = ShowcaseConfig()
            config.delay = 300
            val sequence = MaterialShowcaseSequence(this, "1234")
            sequence.setConfig(config)
            sequence.addSequenceItem(binding.fab, getString(R.string.showcase_drink_btn), getString(string.showcase_dismiss))
            sequence.addSequenceItem(
                findViewById<View>(R.id.app_bar_settings),
                getString(string.showcase_settings),
                getString(string.showcase_dismiss)
            )
            sequence.addSequenceItem(findViewById<View>(R.id.app_bar_volume), getString(string.showcase_volume), getString(string.showcase_dismiss))
            sequence.start()
        }*/
    }

    private fun showInterstitial() {
        if (interstitial.isLoaded) {
            interstitial.show()
        }
    }

    private fun loadInterstitial() {
        interstitial = InterstitialAd(this)
        interstitial.adUnitId = getString(R.string.admob_interstitial_id)
        interstitial.loadAd(Builder().build())
    }

    private fun showCongratulations() {
        CongratsFragment.newInstance().show(supportFragmentManager, "")
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, mraqs.water.R.layout.activity_home)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun observeUIState() {
        viewModel.uiState.observe(this, Observer { updateUIState(it) })
    }
}
