package mraqs.water.manager

import android.content.Context
import android.telephony.TelephonyManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallManager @Inject constructor(val context: Context) {

    val nowCalling: Boolean
        get() {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return when (telephonyManager.callState) {
                TelephonyManager.CALL_STATE_RINGING -> true
                TelephonyManager.CALL_STATE_OFFHOOK -> true
                TelephonyManager.CALL_STATE_IDLE -> false
                else -> false
            }
        }
}