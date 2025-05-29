package com.saha.globepicker.helper

import android.content.Context
import android.telephony.TelephonyManager
import java.util.Locale

object Helpers {

    /**
     * Returns the SIM country ISO code (e.g., "BD" for Bangladesh).
     * Returns null if SIM is unavailable or country can't be determined.
     */
    fun getSimCountryCode(context: Context): String? {
        return try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.simCountryIso?.takeIf { it.isNotEmpty() }?.uppercase(Locale.US)
        } catch (e: Exception) {
            null
        }
    }
}