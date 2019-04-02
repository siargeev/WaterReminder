package mraqs.water.ui.main.settings

import android.os.Bundle
import mraqs.water.R.layout
import mraqs.water.ui.main.BaseActivity

class SettingsActivity : BaseActivity(1) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_settings)
        setupBottomNavigation()
    }
}
