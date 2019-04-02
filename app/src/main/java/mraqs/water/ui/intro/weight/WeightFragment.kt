package mraqs.water.ui.intro.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import mraqs.water.R
import mraqs.water.databinding.IntroWeightFragmentBinding

class WeightFragment : Fragment() {

    companion object {
        fun newInstance() = WeightFragment()
    }

    private lateinit var viewModel: WeightViewModel
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
        viewModel = ViewModelProviders.of(this).get(WeightViewModel::class.java)
        setupBinding()
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
