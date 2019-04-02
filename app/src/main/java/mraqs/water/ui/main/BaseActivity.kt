package mraqs.water.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bottom_navigation_view.bottom_navigation_view
import mraqs.water.ui.main.home.HomeActivity
import mraqs.water.R
import mraqs.water.ui.main.settings.SettingsActivity

abstract class BaseActivity(private val navItem: Int) : AppCompatActivity() {

    private val TAG = "BaseActivity"

    fun setupBottomNavigation() {

        with(bottom_navigation_view) {
            menu.getItem(navItem).isChecked = true
//            setTextVisibility(false)
            setIconVisibility(false)
//            setIconSize(30f, 30f)
            isItemHorizontalTranslationEnabled = false
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