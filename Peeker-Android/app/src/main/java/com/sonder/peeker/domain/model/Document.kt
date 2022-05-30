package com.sonder.peeker.domain.model

data class Document(
    val created_at: String,
    val description: String,
    val document_type: String,
    val emission_date: String,
    val expiration_date: String,
    val name: String,
    val updated_at: String,
    val url: String,
    val id: String,
    val favorite: Boolean,
)
