package com.sonder.peeker.domain.model

data class Session(
    val id: String,
    val user_id: String,
    val token: String,
)