package com.sonder.peeker.domain.model

data class Tag(
    val name: String,
    val id: String,
    val created_at: String,
    val updated_at: String,
    val user_id: String,
    val url: String
)

