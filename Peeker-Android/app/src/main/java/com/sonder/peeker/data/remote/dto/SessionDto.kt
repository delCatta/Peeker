package com.sonder.peeker.data.remote.dto

import com.sonder.peeker.domain.model.Document
import com.sonder.peeker.domain.model.Session

data class SessionDto(
    val created_at: String,
    val id: String,
    val ip_address: String,
    val updated_at: String,
    val user_agent: String,
    val user_id: String
)
fun SessionDto.toSession(token : String): Session {
    return Session(
        id= id,
        user_id = user_id,
        token = token
    )
}