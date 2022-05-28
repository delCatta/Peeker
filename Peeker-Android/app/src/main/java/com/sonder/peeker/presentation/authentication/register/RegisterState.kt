package com.sonder.peeker.presentation.authentication.register

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class RegisterState(
    val isLoading: Boolean? = null,
    val error: String? = null
)