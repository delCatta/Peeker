package com.sonder.peeker.presentation.notification_list

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.core.Resource
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.model.Notification
import com.sonder.peeker.domain.use_case.create_user.CreateUserUseCase
import com.sonder.peeker.domain.use_case.get_notification.GetNotificationUseCase
import com.sonder.peeker.domain.use_case.get_notifications.GetNotificationsUseCase
import com.sonder.peeker.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationSettingViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf<NotificationSettingState>(
        NotificationSettingState(
            notifications_enabled = true,
            days_about_to_expire = 30,
        )
    )
    val state: State<NotificationSettingState> = _state

    init {
        _state.value = _state.value.copy(
            notifications_enabled = sessionManager.getUser()?.notifications_enabled,
            days_about_to_expire = sessionManager.getUser()?.days_about_to_expire
        )

    }

    fun isNotifying(): Boolean {
        return _state.value.notifications_enabled ?: sessionManager.getUser()?.notifications_enabled
        ?: true
    }

    fun updateUser(navController:NavController) {
        if (hasChanged())
            createUserUseCase.updateCurrentUser(getNotificationEnabled(), getDaysToExpire())
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.d("Success", result.data.toString())
                            navController.navigate(Screen.NotificationsScreen.route)
                            _state.value = state.value.copy(isLoading = false)

                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(isLoading = true)
                        }
                        is Resource.Error -> {
                            Log.d("Error", result.message.toString())
                            _state.value = state.value.copy(
                                isLoading = false,
                                error = result.message ?: Constants.UNEXPECTER_ERROR
                            )
                        }
                    }
                }.launchIn(viewModelScope)
    }

    fun getNotificationEnabled(): Boolean {
        return _state.value.notifications_enabled ?: true
    }

    fun getDaysToExpire(): Int {
        return _state.value.days_about_to_expire ?: 30
    }

    fun toggleNotificationEnabled() {
        _state.value = _state.value.copy(
            notifications_enabled = !_state.value.notifications_enabled!!,
            has_changed = true
        )
    }

    fun setDaysToExpire(value: Int) {
        _state.value = _state.value.copy(days_about_to_expire = value, has_changed = true)
    }

    fun hasChanged(): Boolean {
        return _state.value.has_changed
    }

}

data class NotificationSettingState(
    val notifications_enabled: Boolean?,
    val has_changed: Boolean = false,
    val days_about_to_expire: Int?,
    val isLoading: Boolean = false,
    val error: String = ""
)