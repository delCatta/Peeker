package com.sonder.peeker

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PeekerApp : Application(){
    private val context: Context? = null
    fun getAppContext(): Context? {
        return this.context
    }
}