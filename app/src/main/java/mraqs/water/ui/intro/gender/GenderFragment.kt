package mraqs.water.ui.intro.gender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import mraqs.water.R
import mraqs.water.databinding.IntroGenderFragmentBinding

class GenderFragment : Fragment() {

    companion object {
        fun newInstance() = GenderFragment()
    }

    private lateinit var viewModel: GenderViewModel
    private lateinit var binding: IntroGenderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<IntroGenderFragmentBinding>(inflater, R.layout.intro_gender_fragment, container, false)
            .apply {
                binding = this
            }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GenderViewModel::class.java)
        setupBinding()
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}
