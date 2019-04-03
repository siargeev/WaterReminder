package mraqs.water.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_home.btnDrink
import mraqs.water.R
import mraqs.water.data.model.Preference
import mraqs.water.databinding.ActivityHomeBinding
import mraqs.water.manager.PreferenceManager
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
        val prefManager = PreferenceManager(Preference(this))
        btnDrink.setOnClickListener {
            Log.d(TAG, "gender: ${prefManager.loadGender()}")
            Log.d(TAG, "weight: ${prefManager.loadWeight()}")
            Log.d(TAG, "time: ${prefManager.loadActivityTime()}")
            Log.d(TAG, "goal: ${prefManager.loadWaterGoal()}")
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}