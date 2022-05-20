package com.sonder.peeker.data.remote.dto

import com.sonder.peeker.domain.model.Document

data class DocumentDto(
    val created_at: String,
    val description: String,
    val document_type: String,
    val emission_date: String,
    val expiration_date: String,
    val id: Int,
    val name: String,
    val updated_at: String,
    val url: String,
    val user_id: Int
)
fun DocumentDto.toDocument(): Document{
    return Document(created_at= created_at,
    description=description,
    document_type= document_type,
     emission_date= emission_date,
     expiration_date= expiration_date,
     name=name,
     updated_at=updated_at,
     url=url,
     )
}