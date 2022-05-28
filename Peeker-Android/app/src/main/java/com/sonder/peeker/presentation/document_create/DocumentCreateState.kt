package com.sonder.peeker.presentation.document_create

import java.time.LocalDate
import java.util.*

data class DocumentCreateState(
    val documentName: String? = null,
    val documentDescription: String? = null,
    val documentType: Int? = null,
    val documentDateOfIssue: String? = null,
    val documentExpirationDate: String? = null,
    val documentTags: List<String>? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
