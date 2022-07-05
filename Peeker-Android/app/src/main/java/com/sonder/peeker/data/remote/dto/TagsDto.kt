package com.sonder.peeker.data.remote.dto

import com.sonder.peeker.domain.model.Tag

data class TagDto(
    val name: String,
    val id: String,
    val created_at: String,
    val updated_at: String,
    val user_id: String,
    val url: String
)

fun TagDto.toTag(): Tag {
    return Tag(
        name = name,
        id = id,
        created_at = created_at,
        updated_at = updated_at,
        user_id = user_id,
        url = url
    )
}