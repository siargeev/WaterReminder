package mraqs.water.ui.main.home

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
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
import mraqs.water.ui.main.BaseActivity
import mraqs.water.ui.main.home.HomeViewModel.UIState
import mraqs.water.ui.main.home.HomeViewModel.UIState.Congratulations
import mraqs.water.ui.main.home.HomeViewModel.UIState.RequestBatteryPermission
import mraqs.water.ui.main.home.HomeViewModel.UIState.RequestOverlayPermission
import mraqs.water.ui.main.home.HomeViewModel.UIState.ShowInterstitial
import mraqs.water.ui.main.home.HomeViewModel.UIState.Showcase
import mraqs.water.util.show
import javax.inject.Inject

class HomeActivity : BaseActivity(0) {

    companion object {
        const val REQUEST_OVERLAY = 5547
        const val REQUEST_BATTERY = 5548
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
        setupStatusBar()
        observeUIState()
        requestOverlayPermission()
        adView.show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (requestCode) {
                REQUEST_OVERLAY ->
                    if (viewModel.permissionManager.isForbiddenToDrawOverlay) {
                        Log.d(TAG, "onActivityResult: REQUEST_OVERLAY denied")
                    } else {
                        viewModel.startOverlay()
                        Log.d(TAG, "onActivityResult: REQUEST_OVERLAY granted")
                    }
            }
        }
    }

    private fun updateUIState(state: UIState) {
        when (state) {
            is Showcase -> startShowcase()
            is RequestOverlayPermission -> requestOverlayPermission()
            is RequestBatteryPermission -> requestBatteryPermission()
            is ShowInterstitial -> showInterstitial()
            is Congratulations -> showCongratulations()
        }
    }

    private fun showCongratulations() {
        CongratsFragment.newInstance().show(supportFragmentManager, "")
    }

    @TargetApi(VERSION_CODES.M)
    private fun requestOverlayPermission() {
        if (viewModel.permissionManager.isForbiddenToDrawOverlay) {
            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), REQUEST_OVERLAY)
        }
    }

    @TargetApi(VERSION_CODES.M)
    @SuppressLint("BatteryLife")
    private fun requestBatteryPermission() {
        if (viewModel.permissionManager.isNotIgnoreBatteryOptimization) {
            startActivityForResult(Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:$packageName")), REQUEST_BATTERY)
        }
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

    private fun setupStatusBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
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

    private fun loadInterstitial() {
        interstitial = InterstitialAd(this)
        interstitial.adUnitId = getString(R.string.admob_interstitial_id)
        interstitial.loadAd(Builder().build())
    }

    private fun showInterstitial() {
        if (interstitial.isLoaded) {
            interstitial.show()
        }
    }
}