package mraqs.water.ui.main.settings

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import mraqs.water.R.layout
import mraqs.water.ui.main.BaseActivity
import javax.inject.Inject

class SettingsActivity : BaseActivity(1) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingsViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_settings)
        setupBottomNavigation()
    }
}
