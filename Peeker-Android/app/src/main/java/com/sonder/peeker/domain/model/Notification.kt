package com.sonder.peeker.domain.model

data class Notification(
    val id: String,
    val data: Map<out String?, Any>,
    val heading: String,
    val subtitle: String?,
    val content: String,
    val created_at: String,
)