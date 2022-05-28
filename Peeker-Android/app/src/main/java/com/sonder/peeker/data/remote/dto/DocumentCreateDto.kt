package com.sonder.peeker.data.remote.dto

data class DocumentCreateDto(
    val description: String,
    val document_type: Int,
    val emission_date: String,
    val expiration_date: String,
    //val id: String,
    val name: String,
    //val url: String,
    val user_id: String)

