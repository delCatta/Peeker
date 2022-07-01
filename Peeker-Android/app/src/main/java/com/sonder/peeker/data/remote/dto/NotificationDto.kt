package com.sonder.peeker.data.remote.dto

import com.sonder.peeker.domain.model.Notification

data class NotificationDto(
    val id: String,
    val data: Map<String?, Any>?,
    val heading: String,
    val subtitle: String,
    val content: String,
    val created_at: String,
    val updated_at: String?,
    val user_id: String?
)

fun NotificationDto.toNotification(): Notification {
    return Notification(
        id = id,
        data = data ?: emptyMap<String, Any>(),
        heading = heading,
        subtitle = subtitle,
        content = content,
        created_at = created_at,
    )
}
