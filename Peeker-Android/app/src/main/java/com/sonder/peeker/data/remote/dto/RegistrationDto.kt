package com.sonder.peeker.data.remote.dto

data class RegistrationDto(
    val email: String,
    val last_name: String,
    val name: String,
    val password: String,
    val password_confirmation: String
)