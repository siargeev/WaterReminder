package mraqs.water.ui.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.intro_weight_fragment.btnNext
import kotlinx.android.synthetic.main.intro_weight_fragment.pickerWeight
import kotlinx.android.synthetic.main.intro_weight_fragment.pickerWeightUnit
import mraqs.water.R.string
import mraqs.water.databinding.IntroWeightFragmentBinding
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener
import mraqs.water.ui.weight.WeightViewModel.ViewState
import mraqs.water.ui.weight.WeightViewModel.ViewState.NextScreen
import mraqs.water.util.toWeightUnit
import javax.inject.Inject

class WeightFragment : DaggerDialogFragment(), OnNextClickListener {
    companion object {

        fun newInstance(param: String?): WeightFragment {
            val fragment = WeightFragment()
            val args = Bundle()
            args.putString("param", param)
            fragment.arguments = args
            return fragment
        }

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
        return DataBindingUtil.inflate<IntroWeightFragmentBinding>(inflater, mraqs.water.R.layout.intro_weight_fragment, container, false)
            .apply { binding = this }
            .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBinding()
        setupScreen()
        observeViewState()
        setupPickers()
    }

    private fun setupScreen() {
        if (arguments?.getString("param") != null) {
            btnNext.text = getString(string.save_button)
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer {
            updateViewState(it)
        })
    }

    private fun updateViewState(state: ViewState) {
        when (state) {
            is NextScreen -> showNextScreen()
        }
    }

    private fun showNextScreen() {
        if (arguments?.getString("param") != null) {
            dismiss()
        } else
            onClickNext(activity as IntroActivity)
    }

    private fun setupPickers() {
        setupNumberPickers()
        setupUnitPicker()
    }

    private fun setupNumberPickers() {
        pickerWeight.refreshByNewDisplayedValues(viewModel.provideWeights())
        pickerWeight.setOnValueChangedListener { _, _, weight -> viewModel.updateWeight(weight) }
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

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, mraqs.water.R.style.FullScreenDialogStyle)
    }
}
