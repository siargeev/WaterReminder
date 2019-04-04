package mraqs.water.ui.intro.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.intro_weight_fragment.pickerWeight
import kotlinx.android.synthetic.main.intro_weight_fragment.pickerWeightUnit
import mraqs.water.R
import mraqs.water.databinding.IntroWeightFragmentBinding
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener
import mraqs.water.ui.intro.weight.WeightViewModel.ViewState
import mraqs.water.ui.intro.weight.WeightViewModel.ViewState.NextScreen
import mraqs.water.util.toWeightUnit
import javax.inject.Inject

class WeightFragment : DaggerFragment(), OnNextClickListener {
    companion object {

        fun newInstance() = WeightFragment()
        private const val TAG = "WeightFragment"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: WeightViewModel by lazy { ViewModelProviders.of(this, factory).get(WeightViewModel::class.java) }

    private lateinit var binding: IntroWeightFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<IntroWeightFragmentBinding>(inflater, R.layout.intro_weight_fragment, container, false)
            .apply { binding = this }
            .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBinding()
        observeViewState()
        setupPickers()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer {
            updateViewState(it)
        })
    }

    private fun updateViewState(state: ViewState) {
        when (state) {
            is NextScreen -> onClickNext(activity as IntroActivity)
        }
    }

    private fun setupPickers() {
        setupNumberPickers()
        setupUnitPicker()
    }

    private fun setupNumberPickers() {
        pickerWeight.refreshByNewDisplayedValues(viewModel.provideWeights())
        pickerWeight.setOnValueChangedListener{_, _, weight -> viewModel.updateWeight(weight)}
    }

    private fun setupUnitPicker() {
        pickerWeightUnit.refreshByNewDisplayedValues(viewModel.units)
        pickerWeightUnit.setOnValueChangedListener { _, _, unit -> viewModel.updateUnit(unit.toWeightUnit()) }
        viewModel.unit.observe(this, Observer {
            pickerWeight.refreshByNewDisplayedValues(viewModel.provideWeights(it))
        })
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onClickNext(activity: IntroActivity) {
        activity.pager.goToNextSlide()
    }
}
