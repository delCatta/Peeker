package com.sonder.peeker.presentation.document_list

import com.sonder.peeker.domain.model.Document

data class DocumentListState(
    val isLoading: Boolean = false,

    val favoritesSelected: Boolean = false,
    val expiredSelected: Boolean = false,
    val allSelected: Boolean = false,

    val selectedTagIndex: Int? = null,

    val error: String = ""
)
