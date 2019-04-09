package mraqs.water.manager

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.PowerManager
import android.provider.Settings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(private var applicationContext: Context) {

    val isForbiddenToDrawOverlay: Boolean
        get() {
            return if (VERSION.SDK_INT >= VERSION_CODES.M) {
                !Settings.canDrawOverlays(applicationContext)
            } else {
                false
            }
        }

    val isNotIgnoreBatteryOptimization: Boolean
        get() {
            return if (VERSION.SDK_INT >= VERSION_CODES.M) {
                !(applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(applicationContext.packageName)
            } else {
                false
            }
        }
}