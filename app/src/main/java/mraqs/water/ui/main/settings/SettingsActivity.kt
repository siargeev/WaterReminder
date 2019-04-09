package mraqs.water.ui.main.settings

import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_settings.toolbar
import mraqs.water.R
import mraqs.water.databinding.ActivitySettingsBinding
import mraqs.water.ui.activity.ActivityFragment
import mraqs.water.ui.weight.WeightFragment
import mraqs.water.ui.main.BaseActivity
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.ActivityChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.UnitsChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.VolumeChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.WeightChooser
import mraqs.water.ui.volume.VolumeFragment
import javax.inject.Inject

class SettingsActivity : BaseActivity(1) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingsViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java) }
    private lateinit var binding: ActivitySettingsBinding
    private val TAG = "SettingsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomNavigation()
        setupStatusBar()
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { updateViewState(it) })
    }

    private fun updateViewState(state: ViewState): Any = when (state) {
        is UnitsChooser -> showUnitsChooser()
        is WeightChooser -> showWeightChooser()
        is ActivityChooser -> showActivityChooser()
        is VolumeChooser -> showVolumeChooser()
    }

    private fun showUnitsChooser() {
        VolumeFragment.newInstance().show(supportFragmentManager, "")
    }

    private fun showWeightChooser() {
        WeightFragment.newInstance("settings").show(supportFragmentManager, "")
    }

    private fun showActivityChooser() {
        ActivityFragment.newInstance("settings").show(supportFragmentManager, "")
    }

    private fun showVolumeChooser() {
        VolumeFragment.newInstance().show(supportFragmentManager, "")
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun setupStatusBar() {
        setSupportActionBar(toolbar)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = resources.getColor(R.color.white)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}
