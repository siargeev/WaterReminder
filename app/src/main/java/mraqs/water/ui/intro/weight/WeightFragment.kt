package mraqs.water.ui.intro.weight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.intro_weight_fragment.btnMinus
import kotlinx.android.synthetic.main.intro_weight_fragment.btnNext
import kotlinx.android.synthetic.main.intro_weight_fragment.btnPlus
import mraqs.water.R
import mraqs.water.databinding.IntroWeightFragmentBinding
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener

class WeightFragment : Fragment(), OnNextClickListener {
    companion object {

        fun newInstance() = WeightFragment()
        private const val TAG = "WeightFragment"
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

        btnPlus.setOnClickListener { Log.d(TAG, "+") }
        btnMinus.setOnClickListener { Log.d(TAG, "-") }
        btnNext.setOnClickListener { onClickNext(activity as IntroActivity) }
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onClickNext(activity: IntroActivity) {
        activity.pager.goToNextSlide()
    }
}
