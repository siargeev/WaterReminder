package mraqs.water.ui.main

import android.content.Intent
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.bottom_navigation_view
import mraqs.water.R
import mraqs.water.ui.main.home.HomeActivity
import mraqs.water.ui.main.settings.SettingsActivity
import javax.inject.Inject

open class BaseActivity @Inject constructor(private val navItem: Int) : DaggerAppCompatActivity() {

    fun setupBottomNavigation() {

        with(bottom_navigation_view) {
            menu.getItem(navItem).isChecked = true
            setIconVisibility(false)
//            setTextVisibility(false)
//            setIconSize(30f, 30f)
            enableItemShiftingMode(false)
            enableShiftingMode(false)
            enableAnimation(false)
            for (i in 0 until menu.size()) {
                setIconTintList(i, null)
                enableShiftingMode(i, false)
            }

            setOnNavigationItemSelectedListener {
                val nextActivity = when (it.itemId) {
                    R.id.nav_item_home -> HomeActivity::class.java
                    R.id.nav_item_settings -> SettingsActivity::class.java
                    else -> null
                }

                if (nextActivity != null) {
                    val intent = Intent(this@BaseActivity, nextActivity)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                }
                false
            }
        }
    }
}