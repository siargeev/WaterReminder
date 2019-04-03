package mraqs.water.ui.intro.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.intro_activity_fragment.btnNext
import mraqs.water.R
import mraqs.water.databinding.IntroActivityFragmentBinding
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener
import mraqs.water.ui.main.home.HomeActivity
import org.jetbrains.anko.startActivity

class ActivityFragment : Fragment(), OnNextClickListener {

    companion object {
        fun newInstance() = ActivityFragment()
    }

    private lateinit var viewModel: ActivityViewModel
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
        viewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        setupBinding()

        btnNext.setOnClickListener { onClickNext(activity as IntroActivity) }
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    override fun onClickNext(activity: IntroActivity) {
        activity.startActivity<HomeActivity>()
    }
}
