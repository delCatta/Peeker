package com.sonder.peeker.presentation.authentication.register
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.sonder.peeker.core.Constants.UNEXPECTER_ERROR
import com.sonder.peeker.core.Resource
import com.sonder.peeker.di.SessionManager
import com.sonder.peeker.domain.use_case.create_session.CreateSessionUseCase
import com.sonder.peeker.domain.use_case.create_user.CreateUserUseCase
import com.sonder.peeker.presentation.document_list.DocumentListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val createUserUseCase: CreateUserUseCase
): ViewModel(){

    private val _state = mutableStateOf<RegisterState>(RegisterState())
    val state: State<RegisterState> = _state

    val nameValue =  mutableStateOf("")
    val lastNameValue =  mutableStateOf("")
    val emailValue =  mutableStateOf("")
    val passwordValue = mutableStateOf("")
    val confirmPasswordValue = mutableStateOf("")

    init {}

    fun register(onSuccess: ()->( Unit )){
        Log.d("Value name",nameValue.value)
        Log.d("Value lastName",lastNameValue.value)
        Log.d("Value email",emailValue.value)
        Log.d("Value password",passwordValue.value)
        Log.d("Value confirmPassword", confirmPasswordValue.value)
        clearErrors()
        createUserUseCase(nameValue.value,lastNameValue.value,emailValue.value,passwordValue.value, confirmPasswordValue.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("Result",result.data.toString())
                    _state.value = state.value.copy(isLoading = false)
                    onSuccess()
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(isLoading = false, error = result.message ?: UNEXPECTER_ERROR)
                }
                is Resource.Loading -> {
                    Log.d("Loading","LOADING")

                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }
    fun clearErrors(){
        _state.value = state.value.copy(error= null, isLoading = false)
    }
}