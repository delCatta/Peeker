package com.sonder.peeker.di

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.onesignal.OneSignal
import com.sonder.peeker.R
import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.Notification
import com.sonder.peeker.domain.model.Tag
import com.sonder.peeker.domain.model.User


class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private val _state = mutableStateOf<User?>(null )
    val state: State<User?> = _state

    var favoriteDocuments: List<Document> = listOf<Document>()
    var expiredDocuments: List<Document> = listOf<Document>()
    var allDocuments: List<Document> = listOf<Document>()
    var tagDocuments: List<Document> = listOf<Document>()
    var tags: List<Tag> = emptyList()

    var notifications: List<Notification> = listOf<Notification>()

    fun setUser(user: User){
        _state.value = user
        OneSignal.setExternalUserId(user.id)
        OneSignal.setEmail(user.email)
    }

    companion object {
        const val USER_TOKEN = "user_token"
        const val SESSION_ID = "session_id"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String, sessionId : String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(SESSION_ID, sessionId)
        editor.apply()
    }

    fun logOut() {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, null)
        editor.putString(SESSION_ID, null)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }fun fetchSessionId(): String? {
        return prefs.getString(SESSION_ID ,null)
    }
    fun isAuth(): Boolean{
        return !fetchAuthToken().isNullOrEmpty()
    }
    fun getUser(): User?{
        return state.value
    }
}