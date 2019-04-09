package mraqs.water.ui.intro.gender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.intro_gender_fragment.ivFemale
import kotlinx.android.synthetic.main.intro_gender_fragment.ivMale
import mraqs.water.R
import mraqs.water.databinding.IntroGenderFragmentBinding
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener
import mraqs.water.ui.intro.gender.GenderViewModel.ViewState
import mraqs.water.ui.intro.gender.GenderViewModel.ViewState.FemaleActive
import mraqs.water.ui.intro.gender.GenderViewModel.ViewState.MaleActive
import mraqs.water.ui.intro.gender.GenderViewModel.ViewState.NextScreen
import javax.inject.Inject

class GenderFragment : DaggerFragment(), OnNextClickListener {
    companion object {

        fun newInstance() = GenderFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: GenderViewModel by lazy { ViewModelProviders.of(this, factory).get(GenderViewModel::class.java) }
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
        setupBinding()
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { updateViewState(it) })
    }

    private fun updateViewState(state: ViewState) {
        when (state) {
            is NextScreen -> onClickNext(activity as IntroActivity)
            is MaleActive -> makeMaleActive()
            is FemaleActive -> makeFemaleActive()
        }
    }

    private fun makeMaleActive() {
        ivMale.setImageResource(R.drawable.male_active)
        ivFemale.setImageResource(R.drawable.female_inactive)
    }

    private fun makeFemaleActive() {
        ivMale.setImageResource(R.drawable.male_inactive)
        ivFemale.setImageResource(R.drawable.female_active)
    }

    override fun onClickNext(activity: IntroActivity) {
        activity.pager.goToNextSlide()
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }


}
