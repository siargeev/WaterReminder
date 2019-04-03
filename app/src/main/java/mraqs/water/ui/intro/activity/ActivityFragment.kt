package mraqs.water.ui.intro.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import mraqs.water.R
import mraqs.water.databinding.IntroActivityFragmentBinding
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener
import mraqs.water.ui.intro.activity.ActivityViewModel.ViewState
import mraqs.water.ui.intro.activity.ActivityViewModel.ViewState.NextScreen
import mraqs.water.ui.main.home.HomeActivity
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class ActivityFragment : DaggerFragment(), OnNextClickListener {

    companion object {
        fun newInstance() = ActivityFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: ActivityViewModel by lazy { ViewModelProviders.of(this, factory).get(ActivityViewModel::class.java) }
    private lateinit var binding: IntroActivityFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<IntroActivityFragmentBinding>(inflater, R.layout.intro_activity_fragment, container, false)
            .apply { binding = this }.root
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
        }
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onClickNext(activity: IntroActivity) {
        activity.startActivity<HomeActivity>()
    }
}
