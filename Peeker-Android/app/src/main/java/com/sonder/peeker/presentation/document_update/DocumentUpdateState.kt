package com.sonder.peeker.presentation.document_update

import com.sonder.peeker.domain.model.Document

data class DocumentUpdateState(
    val document: Document? = null,
    val documentName: String? = null,
    val documentDescription: String? = null,
    val documentType: Int? = null,
    val documentDateOfIssue: String? = null,
    val documentExpirationDate: String? = null,
    val documentTags: List<String>? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
