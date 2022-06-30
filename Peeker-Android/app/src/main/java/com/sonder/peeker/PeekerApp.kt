package com.sonder.peeker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.onesignal.OneSignal
import com.sonder.peeker.core.Constants.ONESIGNAL_APP_ID
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PeekerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Peeker", "ON CREATE")
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        Log.d(
            "Notifications",
            if (NotificationManagerCompat.from(this)
                    .areNotificationsEnabled()
            ) "enabled" else "not enabled"
        )


    }

    private val context: Context? = null
    fun getAppContext(): Context? {
        return this.context
    }
}