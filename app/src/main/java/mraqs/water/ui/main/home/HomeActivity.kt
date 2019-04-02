package mraqs.water.ui.main.home

import android.os.Bundle
import mraqs.water.R.layout
import mraqs.water.ui.main.BaseActivity

class HomeActivity : BaseActivity(0) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)
        setupBottomNavigation()
    }
}
