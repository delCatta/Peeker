package com.sonder.peeker.data.remote.dto

import com.sonder.peeker.domain.model.Document

data class DocumentDto(
    val created_at: String,
    val description: String,
    val document_type: Int,
    val emission_date: String,
    val expiration_date: String,
    val expired: Boolean,
    val id: String,
    val name: String,
    val updated_at: String,
    val url: String,
    val user_id: String,
    val favorite: Boolean,
    val tags: List<TagsDto>
)

fun DocumentDto.toDocument(): Document {
    return Document(
        created_at = created_at,
        description = description,
        document_type = document_type,
        emission_date = emission_date,
        expiration_date = expiration_date,
        expired = expired,
        name = name,
        updated_at = updated_at,
        url = url,
        id = id,
        favorite = favorite,
        tags = tags
    )
}