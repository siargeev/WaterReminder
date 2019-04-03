package mraqs.water.ui.main.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import mraqs.water.R
import mraqs.water.databinding.ActivityHomeBinding
import mraqs.water.ui.main.BaseActivity
import javax.inject.Inject

class HomeActivity : BaseActivity(0) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java) }
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomNavigation()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}