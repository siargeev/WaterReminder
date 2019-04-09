package mraqs.water.ui.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import mraqs.water.R
import mraqs.water.databinding.ActivitySplashBinding
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by lazy { ViewModelProviders.of(this, factory).get(SplashViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        viewModel.startLoading(this)
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
        binding.splash.performAnimation()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}




