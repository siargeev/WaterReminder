package mraqs.water.ui.warning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_warning.btnNext
import mraqs.water.R
import mraqs.water.ui.intro.IntroActivity
import mraqs.water.ui.intro.IntroActivity.OnNextClickListener
import javax.inject.Inject

class WarningFragment : DaggerFragment(), OnNextClickListener {
    companion object {
        fun newInstance() = WarningFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    val viewModel: WarningViewModel by lazy { ViewModelProviders.of(this, factory).get(WarningViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_warning, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnNext.setOnClickListener { onClickNext(activity as IntroActivity) }
    }

    override fun onClickNext(activity: IntroActivity) {
        activity.pager.goToNextSlide()
    }
}
