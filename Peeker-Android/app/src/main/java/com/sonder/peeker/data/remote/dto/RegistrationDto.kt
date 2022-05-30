package com.sonder.peeker.data.remote.dto

data class RegistrationDto(
    val name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)