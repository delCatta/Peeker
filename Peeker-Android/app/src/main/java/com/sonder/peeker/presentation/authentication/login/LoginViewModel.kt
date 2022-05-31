package com.sonder.peeker.presentation.authentication.login
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants
import com.sonder.peeker.core.Resource
import com.sonder.peeker.data.remote.dto.toSession
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.use_case.create_session.CreateSessionUseCase
import com.sonder.peeker.domain.use_case.create_user.CreateUserUseCase
import com.sonder.peeker.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val createSessionUseCase: CreateSessionUseCase
): ViewModel(){

    private val _state = mutableStateOf<LoginState>(LoginState())
    val state: State<LoginState> = _state



    val emailValue =  mutableStateOf("d@c.c")
    val passwordValue = mutableStateOf("123456")

    init {

    }

    fun login(navController: NavController){
        Log.d("Value email",emailValue.value)
        Log.d("Value password",passwordValue.value)
        clearErrors()
        createSessionUseCase(emailValue.value,passwordValue.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val session = result.data
                    Log.d("Session",session.toString())

                    navController.navigate(Screen.HomeScreen.route)
                    _state.value = state.value.copy(isLoading = false)

                }
                is Resource.Error -> {
                    _state.value = state.value.copy(isLoading = false, error = result.message ?: Constants.UNEXPECTER_ERROR)
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun clearErrors(){
        _state.value = state.value.copy(error= null, isLoading = false)
    }
    fun setCredentials(email:String,password:String){
        emailValue.value = email
        passwordValue.value = password
    }
}