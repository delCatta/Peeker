package com.sonder.peeker.data.remote.dto

import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.User

data class UserDto(
    val created_at: String,
    val email: String,
    val id: String,
    val last_name: String,
    val name: String,
    val password_digest: String,
    val updated_at: String,
    val verified: Boolean
)
fun UserDto.toUser(): User {
    return User(
        email = email,
        name = name,
        last_name = last_name,
    )
}