package com.sonder.peeker.presentation.authentication

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Resource
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.use_case.create_session.CreateSessionUseCase
import com.sonder.peeker.domain.use_case.get_current_user.GetCurrentUserUseCase
import com.sonder.peeker.presentation.Screen
import com.sonder.peeker.presentation.authentication.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {


    private val _state = mutableStateOf<AuthState>(AuthState())
    val state: State<AuthState> = _state

    init {
        var token = getToken()
        if (token.isNullOrEmpty()) {
            Log.d("Auth","Null Session Token")
        }else{
            Log.d("Auth","Logged In: $token")
            getCurrentUserUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val user = result.data!!
                        sessionManager.setUser(user)
                        Log.d("Auth",user.name)
//                        _state.value = state.value.copy(isLoading = false, isAuth = true)
                    }
                    is Resource.Error -> {
                        Log.d("Auth","Error ${result.message?:""}")
//                        _state.value = state.value.copy(isLoading = false, error = result.message ?: Constants.UNEXPECTER_ERROR)
                    }
                    is Resource.Loading -> {
                        Log.d("Auth","Loading")
                        _state.value = state.value.copy(isLoading = true, isAuth= false)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    fun getToken(): String?{
        return sessionManager.fetchAuthToken()
    }
    fun logOut(){
        sessionManager.logOut()
    }


}

data class AuthState(
    val isAuth: Boolean= false,
    val isLoading: Boolean = false,
    val error: String? = null,
    )