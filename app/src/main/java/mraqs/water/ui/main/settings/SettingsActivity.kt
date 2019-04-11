package mraqs.water.ui.main.settings

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_settings.adView
import mraqs.water.R
import mraqs.water.R.string
import mraqs.water.databinding.ActivitySettingsBinding
import mraqs.water.ui.activity.ActivityFragment
import mraqs.water.ui.interval.IntervalFragment
import mraqs.water.ui.main.BaseActivity
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.ActivityChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.IntervalChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.PrivacyPolicy
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.TermsOfUse
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.UnitsChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.UpdateTimeUnit
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.VolumeChooser
import mraqs.water.ui.main.settings.SettingsViewModel.ViewState.WeightChooser
import mraqs.water.ui.volume.VolumeFragment
import mraqs.water.ui.weight.WeightFragment
import mraqs.water.util.show
import org.jetbrains.anko.browse
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
        observeViewState()
        adView.show()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { updateViewState(it) })
    }

    private fun updateViewState(state: ViewState): Any = when (state) {
        is UnitsChooser -> showUnitsChooser()
        is WeightChooser -> showWeightChooser()
        is ActivityChooser -> showActivityChooser()
        is VolumeChooser -> showVolumeChooser()
        is PrivacyPolicy -> browse(getString(string.policy_url), true)
        is TermsOfUse -> browse(getString(string.terms_url), true)
        is IntervalChooser -> showIntervalChooser()
        is UpdateTimeUnit -> updateTimeUnit(state.unit)
    }

    private fun updateTimeUnit(unit: String) {
        val newUnit = when (unit) {
            "min" -> getString(R.string.min)
            "hour" -> getString(R.string.hour)
            else -> getString(R.string.min)
        }
        viewModel.timeUnit.set(newUnit)
    }

    private fun showIntervalChooser() {
        IntervalFragment.newInstance().show(supportFragmentManager, "")
    }

    private fun showUnitsChooser() {
        TODO()
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
}
