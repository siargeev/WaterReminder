package mraqs.water.ui.congrats

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.congrats_fragment.adView
import mraqs.water.R
import mraqs.water.databinding.CongratsFragmentBinding
import mraqs.water.ui.congrats.CongratsViewModel.ViewState
import mraqs.water.util.show
import javax.inject.Inject

class CongratsFragment : DaggerDialogFragment() {

    companion object {
        fun newInstance() = CongratsFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: CongratsViewModel by lazy { ViewModelProviders.of(this, factory).get(CongratsViewModel::class.java) }
    private lateinit var binding: CongratsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<CongratsFragmentBinding>(inflater, R.layout.congrats_fragment, container, false)
            .apply { binding = this }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        binding.btnClose.setOnClickListener { dismiss() }
        setupDialogSize()
        observeViewState()
        setupAnimation()
        adView.show()
    }

    private fun setupAnimation() {
        viewModel.startLoading()
        binding.lottieSalut.addAnimatorListener(object : AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                viewModel.stopLoading()
            }
        })
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { updateViewState(it) })
    }

    private fun updateViewState(state: ViewState) {
        when (state) {
            is ViewState.Loading -> showLoading(state.loading)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.lottieSalut.visibility = View.VISIBLE
            binding.btnClose.visibility = View.GONE
            binding.congratsLabel.visibility = View.GONE
            binding.adView.visibility = View.GONE
        } else {
            binding.lottieSalut.visibility = View.GONE
            binding.btnClose.visibility = View.VISIBLE
            binding.congratsLabel.visibility = View.VISIBLE
            binding.adView.visibility = View.VISIBLE
        }
    }

    private fun setupDialogSize() {
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
        dialog?.window?.setLayout(width, height)
    }
}
