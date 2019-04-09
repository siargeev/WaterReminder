package mraqs.water.ui.intro.activity

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
import kotlinx.android.synthetic.main.intro_activity_fragment.btnNext
import mraqs.water.R
import mraqs.water.R.string
import mraqs.water.databinding.IntroActivityFragmentBinding
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener
import mraqs.water.ui.intro.activity.ActivityViewModel.ViewState
import mraqs.water.ui.intro.activity.ActivityViewModel.ViewState.NextScreen
import mraqs.water.ui.main.home.HomeActivity
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class ActivityFragment : DaggerDialogFragment(), OnNextClickListener {

    companion object {
        fun newInstance(param: String?): ActivityFragment {
            val fragment = ActivityFragment()
            val args = Bundle()
            args.putString("param", param)
            fragment.arguments = args
            return fragment
        }
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
        setupScreen()
        observeViewState()
    }

    private fun setupScreen() {
        if (arguments?.getString("param") != null) {
            btnNext.text = getString(string.save_button)
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { updateViewState(it) })
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

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onClickNext(activity: IntroActivity) {
        activity.startActivity<HomeActivity>()
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
