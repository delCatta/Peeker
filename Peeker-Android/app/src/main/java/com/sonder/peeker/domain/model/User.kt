package com.sonder.peeker.domain.model

data class User(
    val id: String,
    val email : String,
    val name : String,
    val last_name : String,
    val notifications_enabled: Boolean,
    val days_about_to_expire: Int,
)
