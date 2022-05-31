package com.sonder.peeker.domain.model

import com.sonder.peeker.core.Constants.DOCUMENT_TYPES
import com.sonder.peeker.data.remote.dto.TagsDto

data class Document(
    val created_at: String,
    val description: String?,
    val document_type: Int?,
    val emission_date: String?,
    val expiration_date: String?,
    val name: String?,
    val updated_at: String?,
    val url: String?,
    val id: String,
    val favorite: Boolean,
    val tags: List<TagsDto>
){
    fun getDocumentType():String{
        try {
        return DOCUMENT_TYPES[document_type?:0]
        }catch (ex: Exception){
            return "No se encontro un tipo."
        }
    }
}
