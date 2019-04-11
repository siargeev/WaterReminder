package mraqs.water.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionManager @Inject constructor(private var applicationContext: Context) {


    companion object {
        const val REQUEST_OVERLAY = 5547
        const val REQUEST_BATTERY = 5548
        const val TAG = "PermissionManager"
    }

    val isForbiddenToDrawOverlay: Boolean
        get() {
            return if (VERSION.SDK_INT >= VERSION_CODES.M) {
                Log.d(TAG, "CanDrawOverlay: ${Settings.canDrawOverlays(applicationContext)}")
                !Settings.canDrawOverlays(applicationContext)
            } else {
                false
            }
        }

    val isNotIgnoreBatteryOptimization: Boolean
        get() {
            return if (VERSION.SDK_INT >= VERSION_CODES.M) {
                Log.d(
                    TAG,
                    "IsIgnoreBatteryOptimizing: ${(applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(
                        applicationContext.packageName
                    )}"
                )
                !(applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager).isIgnoringBatteryOptimizations(applicationContext.packageName)
            } else {
                false
            }
        }

    fun requestPermissions() {
        if (isNotIgnoreBatteryOptimization) requestBatteryPermission()
        if (isForbiddenToDrawOverlay) requestOverlayPermission()
    }

    private fun requestOverlayPermission() {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            Log.d(TAG, "requestOverlayPermission: requested")
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${applicationContext.packageName}"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
        }
    }

    @SuppressLint("BatteryLife")
    private fun requestBatteryPermission() {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            Log.d(TAG, "requestBatteryPermission: requested")
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:${applicationContext.packageName}"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
        }
    }
}