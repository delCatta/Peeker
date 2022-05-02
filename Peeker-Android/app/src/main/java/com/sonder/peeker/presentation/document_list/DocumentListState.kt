package com.sonder.peeker.presentation.document_list

data class DocumentListState(
    val isLoading: Boolean = false,
    val isLoadingTags: Boolean = true,

    val favoritesSelected: Boolean = true,
    val expiredSelected: Boolean = false,
    val allSelected: Boolean = false,
    val tags: List<String> = emptyList(),
    val selectedTagIndex: Int? = null,

    val favoriteDocuments: List<String> = emptyList(),
    val expiredDocuments: List<String> = emptyList(),
    val allDocuments: List<String> = emptyList(),
    val tagDocuments: List<String> = emptyList(),

    val error: String = ""
)
