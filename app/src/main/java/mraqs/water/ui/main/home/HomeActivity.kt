package mraqs.water.ui.main.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import mraqs.water.R.layout
import mraqs.water.ui.main.BaseActivity
import javax.inject.Inject

class HomeActivity : BaseActivity(0) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)
        setupBottomNavigation()
    }
}