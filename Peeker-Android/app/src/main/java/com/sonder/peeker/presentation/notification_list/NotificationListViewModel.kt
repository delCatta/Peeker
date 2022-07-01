package com.sonder.peeker.presentation.notification_list

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.core.Resource
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.model.Notification
import com.sonder.peeker.domain.use_case.get_notification.GetNotificationUseCase
import com.sonder.peeker.domain.use_case.get_notifications.GetNotificationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<NotificationListState>(NotificationListState())
    val state: State<NotificationListState> = _state

    init {
        getAllNotifications()
    }

    fun getAllNotifications() {
        getNotificationsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sessionManager.notifications = result.data ?: emptyList()
                    _state.value = NotificationListState(isLoading = false)

                }
                is Resource.Error -> {
                    _state.value = NotificationListState(
                        error = result.message ?: UNEXPECTER_ERROR
                    )
                }
                is Resource.Loading -> {
                    _state.value = NotificationListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun notifications(): List<Notification> {
        if(_state.value.isLoading) return emptyList<Notification>()
        return sessionManager.notifications
    }

}
data class NotificationListState(
    val isLoading: Boolean = false,
    val error: String = ""
)