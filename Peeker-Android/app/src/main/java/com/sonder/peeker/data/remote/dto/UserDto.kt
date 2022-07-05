package com.sonder.peeker.data.remote.dto

import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.User

data class UserDto(
    val created_at: String,
    val email: String,
    val id: String,
    val last_name: String,
    val name: String,
    val notifications_enabled: Boolean,
    val days_about_to_expire: Int,
    val password_digest: String,
    val updated_at: String,
    val verified: Boolean
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        email = email,
        name = name,
        notifications_enabled = notifications_enabled,
        days_about_to_expire = days_about_to_expire,
        last_name = last_name,
    )
}