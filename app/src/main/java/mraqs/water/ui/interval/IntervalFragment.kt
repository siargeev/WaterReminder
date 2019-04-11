package mraqs.water.ui.interval

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
import kotlinx.android.synthetic.main.interval_fragment.pickerInterval
import kotlinx.android.synthetic.main.interval_fragment.tickerTimeUnit
import mraqs.water.R
import mraqs.water.databinding.IntervalFragmentBinding
import mraqs.water.ui.interval.IntervalViewModel.UIState
import mraqs.water.ui.interval.IntervalViewModel.UIState.SaveClick
import javax.inject.Inject

class IntervalFragment : DaggerDialogFragment() {

    companion object {
        fun newInstance() = IntervalFragment()
        private val TAG = "IntervalFragment"
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: IntervalViewModel by lazy { ViewModelProviders.of(this, factory).get(IntervalViewModel::class.java) }
    private lateinit var binding: IntervalFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<IntervalFragmentBinding>(inflater, R.layout.interval_fragment, container, false)
            .apply { binding = this }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBinding()
        observeUIState()
        setupPicker()
    }

    private fun setupPicker() {
        pickerInterval.refreshByNewDisplayedValues(viewModel.intervals)
        pickerInterval.setOnValueChangedListener { _, _, pos -> updateTicker(pos); viewModel.interval.postValue(pickerInterval.contentByCurrValue) }
    }

    private fun updateTicker(pos: Int) {
        if (pos == 0 || pos == 1 || pos == 2) tickerTimeUnit.text = getString(R.string.min)
        if (pos == 3 || pos == 4 || pos == 5) tickerTimeUnit.text = getString(R.string.hour)
    }

    private fun updateUIState(state: UIState) = when (state) {
        SaveClick -> dismiss()
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

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun observeUIState() {
        viewModel.uiState.observe(this, Observer { updateUIState(it) })
    }
}
