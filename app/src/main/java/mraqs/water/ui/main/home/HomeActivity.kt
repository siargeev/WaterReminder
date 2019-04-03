package mraqs.water.ui.main.home

import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import mraqs.water.databinding.ActivityHomeBinding
import mraqs.water.ui.main.BaseActivity
import javax.inject.Inject

class HomeActivity : BaseActivity(0) {

    private val TAG = "HomeActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java) }
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomNavigation()
        setupStatusBar()
    }

    private fun setupStatusBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, mraqs.water.R.layout.activity_home)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}