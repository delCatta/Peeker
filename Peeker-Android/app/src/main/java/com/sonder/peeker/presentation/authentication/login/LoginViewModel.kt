package com.sonder.peeker.presentation.authentication.login
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel(){

    private val _state = mutableStateOf<LoginState>(LoginState())
    val state: State<LoginState> = _state

    init {}
}